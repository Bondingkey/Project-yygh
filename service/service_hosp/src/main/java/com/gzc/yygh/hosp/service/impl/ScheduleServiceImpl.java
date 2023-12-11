package com.gzc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gzc.yygh.hosp.repostitry.ScheduleRepository;
import com.gzc.yygh.hosp.service.DepartmentService;
import com.gzc.yygh.hosp.service.HospService;
import com.gzc.yygh.hosp.service.ScheduleService;
import com.gzc.yygh.model.hosp.Hospital;
import com.gzc.yygh.model.hosp.Schedule;
import com.gzc.yygh.vo.hosp.BookingScheduleRuleVo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/07  10:11  周四
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private HospService hospService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public void saveSchedule(Map<String, Object> objectMap) {
        Schedule schedule = JSONObject.parseObject(JSONObject.toJSONString(objectMap), Schedule.class);
        String hoscode = schedule.getHoscode();
        String depcode = schedule.getDepcode();
        String hosScheduleId = schedule.getHosScheduleId();

        Schedule Myschedule = scheduleRepository.findByHoscodeAndDepcodeAndHosScheduleId(hoscode,depcode,hosScheduleId);

        if (Myschedule ==null){
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
        }else {
            schedule.setCreateTime(Myschedule.getCreateTime());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(Myschedule.getIsDeleted());
            schedule.setId(Myschedule.getId());
            scheduleRepository.save(Myschedule);
        }
    }

    @Override
    public Page<Schedule> findlist(Map<String, Object> objectMap) {
        Schedule schedule = new Schedule();
        String hoscode = (String) objectMap.get("hoscode");
        schedule.setHoscode(hoscode);
        //设置查询条件
        Example<Schedule> of = Example.of(schedule);

        int page = Integer.parseInt(objectMap.get("page").toString());
        int limit = Integer.parseInt(objectMap.get("limit").toString());

        PageRequest pageRequest = PageRequest.of(page-1,limit, Sort.by("createTime").ascending());
        Page<Schedule> all = scheduleRepository.findAll(of, pageRequest);

        return all;
    }

    //删除方法
    @Override
    public void remove(Map<String, Object> objectMap) {
        String hoscode = (String) objectMap.get("hoscode");
        String hosScheduleId = (String) objectMap.get("hosScheduleId");

        Schedule schedule = scheduleRepository.findByHoscodeAndHosScheduleId(hoscode,hosScheduleId);

        if (schedule!=null){
            scheduleRepository.deleteById(schedule.getId());
        }
    }

    @Override
    public Map<String, Object> page(Integer pageNum, Integer pageSize, String hoscode, String depcode) {

        //聚合:最好使用mongoTemplate
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        //第一次聚合
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
                            .first("workDate")
                            .as("workDate")
                            .count().as("docCount")
                            .sum("reservedNumber").as("reservedNumber")
                            .sum("availableNumber").as("availableNumber"),
                Aggregation.sort(Sort.Direction.ASC,"workDate"),
                Aggregation.skip((pageNum-1)*pageSize),
                Aggregation.limit(pageSize)

        );//聚合条件
        //开始聚合
        AggregationResults<BookingScheduleRuleVo> aggregate = mongoTemplate.aggregate(aggregation, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> mappedResults = aggregate.getMappedResults();
        for (BookingScheduleRuleVo bookingScheduleRuleVo : mappedResults) {
            Date workDate = bookingScheduleRuleVo.getWorkDate();
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);
        }

        //第二次聚合
        //聚合:最好使用mongoTemplate
        Aggregation aggregation2 = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );//聚合条件
        AggregationResults<BookingScheduleRuleVo> aggregate2 = mongoTemplate.aggregate(aggregation2, Schedule.class, BookingScheduleRuleVo.class);

        //需要准备的数据
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list",mappedResults);
        map.put("total",aggregate2.getMappedResults().size());

        //获取医院名称
        Hospital hospital = hospService.getHospByHospCode(hoscode);
        //其他基础数据
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname",hospital.getHosname());
        map.put("baseMap",baseMap);
        return map;
    }

    @Override
    public List<Schedule> detail(String hoscode, String depcode, String workdata) {
        Date date = new DateTime(workdata).toDate();
        List<Schedule> ScheduleList = scheduleRepository.findByHoscodeAndDepcodeAndWorkDate(hoscode,depcode,date);
        //把得到list集合遍历，向设置其他值：医院名称、科室名称、日期对应星期
        ScheduleList.stream().forEach(item->{
            this.packageSchedule(item);
        });

        return ScheduleList;
    }

    private void packageSchedule(Schedule schedule) {
        //设置医院名称
        schedule.getParam().put("hosname",hospService.getHospByHospCode(schedule.getHoscode()).getHosname());
        //设置科室名称
        schedule.getParam().put("depname",
                departmentService.getDepName(schedule.getHoscode(),schedule.getDepcode()));
        //设置日期对应星期
        schedule.getParam().put("dayOfWeek",this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
    }

    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "周日";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "周一";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "周二";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "周三";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "周四";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "周五";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "周六";
            default:
                break;
        }
        return dayOfWeek;
    }
}
