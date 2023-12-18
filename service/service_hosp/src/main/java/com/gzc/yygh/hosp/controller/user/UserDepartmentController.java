package com.gzc.yygh.hosp.controller.user;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.hosp.service.DepartmentService;
import com.gzc.yygh.model.hosp.Department;
import com.gzc.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/13  09:47  周三
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/user/hosp/department")
//@CrossOrigin  //局部解决跨域问题
public class UserDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/all/{hoscode}")
    public R findAll(@PathVariable("hoscode") String hoscode){
        List<DepartmentVo> allDept = departmentService.findAllDept(hoscode);
        return R.ok().data("list",allDept);
    }


}
