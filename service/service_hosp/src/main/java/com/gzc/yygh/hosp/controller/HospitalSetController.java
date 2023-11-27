package com.gzc.yygh.hosp.controller;

import com.gzc.yygh.hosp.service.HospitalSetService;
import com.gzc.yygh.model.hosp.HospitalSet;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author gzc
 * @since 2023-11-27
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @GetMapping("/findAll")
    public List<HospitalSet> findAllHospital(){
        return hospitalSetService.list();
    }

    @DeleteMapping("/deleteById/{id}")
    public boolean deleteHospital(@PathVariable("id") Long id){
        return hospitalSetService.removeById(id);
    }

}

