package com.gzc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gzc.yygh.hosp.repostitry.ScheduleRepository;
import com.gzc.yygh.hosp.service.ScheduleService;
import com.gzc.yygh.model.hosp.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
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
}
