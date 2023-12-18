package com.gzc.yygh.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.common.result.R;
import com.gzc.yygh.common.utils.JwtHelper;
import com.gzc.yygh.model.user.Patient;
import com.gzc.yygh.user.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 就诊人表 前端控制器
 * </p>
 *
 * @author gzc
 * @since 2023-12-18
 */
@RestController
@RequestMapping("/user/userinfo/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    //增
    @PostMapping("/save")
    public R save(@RequestBody Patient patient,@RequestHeader String token){
        Long userId = JwtHelper.getUserId(token);
        patient.setUserId(userId);
        System.out.println("新增userId = " + userId);
        System.out.println("新增patient = " + patient);
        patientService.save(patient);
        return  R.ok();
    }

    //删
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id){
        patientService.removeById(id);
        return R.ok();
    }

    //改回显数据
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Long id){
        Patient patient = patientService.getById(id);
        return R.ok().data("patient",patient);
    }

    //修改
    @PutMapping("/update")
    public R update(@RequestBody Patient patient){
        patientService.updateById(patient);
        return  R.ok();
    }

    //查
    @GetMapping("/all")
    public R findAll(@RequestHeader String token){
        System.out.println("token = " + token);
        List<Patient> list = patientService.findAll(token);
        System.out.println("list = " + list);

        return R.ok().data("list",list);
    }
}
