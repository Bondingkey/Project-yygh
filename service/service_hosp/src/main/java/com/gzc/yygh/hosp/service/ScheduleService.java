package com.gzc.yygh.hosp.service;

import org.springframework.data.domain.Page;
import com.gzc.yygh.model.hosp.Schedule;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/07  10:11  周四
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public interface ScheduleService{
    void saveSchedule(Map<String, Object> objectMap);

    Page<Schedule> findlist(Map<String, Object> objectMap);

    void remove(Map<String, Object> objectMap);

}
