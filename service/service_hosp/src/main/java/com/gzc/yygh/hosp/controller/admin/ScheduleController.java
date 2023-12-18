package com.gzc.yygh.hosp.controller.admin;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.hosp.service.ScheduleService;
import com.gzc.yygh.model.hosp.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/11  09:19  周一
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/admin/hosp/schedule")
//@CrossOrigin  //局部解决跨域问题
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    //查询排班详情
    @GetMapping("/{hoscode}/{depcode}/{workdata}")
    public R detail(@PathVariable("hoscode") String hoscode,
                    @PathVariable("depcode") String depcode,
                    @PathVariable("workdata") String workdata){
        List<Schedule> list = scheduleService.detail(hoscode,depcode,workdata);
        return R.ok().data("list",list);
    }

    //查询排班的分页数据
    @GetMapping("/{pageNum}/{pageSize}/{hoscode}/{depcode}")
    public R page(@PathVariable("pageNum")Integer pageNum,
                  @PathVariable("pageSize")Integer pageSize,
                  @PathVariable("hoscode")String hoscode,
                  @PathVariable("depcode")String depcode){

        Map<String,Object> map = scheduleService.page(pageNum,pageSize,hoscode,depcode);
        return R.ok().data(map);
    }
}
