package com.gzc.yygh.hosp.controller.api;

import com.gzc.yygh.hosp.Result.Result;
import com.gzc.yygh.hosp.service.DepartmentService;
import com.gzc.yygh.hosp.utils.HospUtils;
import com.gzc.yygh.model.hosp.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/06  16:01  周三
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/department/remove")
    public Result removeDepartment(HttpServletRequest httpServletRequest){
        Map<String, Object> objectMap = HospUtils.switchMap(httpServletRequest.getParameterMap());
        departmentService.remove(objectMap);
        return Result.ok();
    }

    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest httpServletRequest){
        Map<String, Object> objectMap = HospUtils.switchMap(httpServletRequest.getParameterMap());
        //验证singkey
        departmentService.saveDepartment(objectMap);
        return Result.ok();
    }

    @PostMapping("/department/list")
    public Result<Page> list(HttpServletRequest httpServletRequest){
        Map<String, Object> objectMap = HospUtils.switchMap(httpServletRequest.getParameterMap());
        Page<Department> page = departmentService.getDepartmentPage(objectMap);
        return Result.ok(page);
    }
}
