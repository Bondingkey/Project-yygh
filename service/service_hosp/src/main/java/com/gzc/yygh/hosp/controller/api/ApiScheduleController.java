package com.gzc.yygh.hosp.controller.api;

import com.gzc.yygh.hosp.Result.Result;
import com.gzc.yygh.hosp.service.ScheduleService;
import com.gzc.yygh.hosp.utils.HospUtils;
import com.gzc.yygh.model.hosp.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/07  10:07  周四
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/schedule/remove")
    public Result delete(HttpServletRequest request){
        Map<String, Object> objectMap = HospUtils.switchMap(request.getParameterMap());
        scheduleService.remove(objectMap);
        return Result.ok();
    }

    @PostMapping("/schedule/list")
    public Result<Page> findschedulepage(HttpServletRequest request){
        Map<String, Object> objectMap = HospUtils.switchMap(request.getParameterMap());
        Page<Schedule> page = scheduleService.findlist(objectMap);
        return Result.ok(page);
    }


    @PostMapping("/saveSchedule")
    public Result updateSchedule(HttpServletRequest request){
        Map<String, Object> objectMap = HospUtils.switchMap(request.getParameterMap());
        scheduleService.saveSchedule(objectMap);
        return Result.ok();
    }
}
