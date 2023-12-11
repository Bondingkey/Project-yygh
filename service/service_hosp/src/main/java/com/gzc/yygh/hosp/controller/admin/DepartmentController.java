package com.gzc.yygh.hosp.controller.admin;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.hosp.service.DepartmentService;
import com.gzc.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/10  09:19  周日
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/admin/hosp/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{hoscode}")
    public R findAllDept(@PathVariable("hoscode") String hoscode){
        List<DepartmentVo>  list = departmentService.findAllDept(hoscode);
        return R.ok().data("list",list);
    }
}
