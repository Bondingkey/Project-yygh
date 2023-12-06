package com.gzc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.common.error.YyghError;
import com.gzc.yygh.hosp.mapper.HospitalSetMapper;
import com.gzc.yygh.hosp.repostitry.HospRepository;
import com.gzc.yygh.hosp.service.HospService;
import com.gzc.yygh.model.hosp.Hospital;
import com.gzc.yygh.model.hosp.HospitalSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/06  10:15  周三
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Service
public class HospServiceImpl implements HospService {

    @Autowired
    private HospRepository hospRepository;

    @Autowired
    private HospitalSetMapper HospitalSetMapper;

    @Override
    public void insert(Map<String, Object> objectMap) {
        //将map转化成json字符串
        String s = JSONObject.toJSONString(objectMap);
        //将json字符串转化成对象
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);
        //保存前进行判断
        String hoscode = hospital.getHoscode();
        Hospital collection = hospRepository.findByHoscode(hoscode);
        if (collection==null){
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            //保存
            hospRepository.save(hospital);
        }else {
            hospital.setStatus(collection.getStatus());
            hospital.setCreateTime(collection.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(collection.getIsDeleted());
            hospital.setId(collection.getId());
            hospRepository.save(hospital);
        }
    }

    @Override
    public String getSignByHoscode(String hoscode) {
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<HospitalSet>();
        queryWrapper.eq("hoscode",hoscode);
        System.out.println(hoscode);
        HospitalSet hospitalSet = HospitalSetMapper.selectOne(queryWrapper);
        if (hospitalSet == null){
            throw new YyghError("该医院信息不存在",20001);
        }
        return hospitalSet.getSignKey();
    }

    @Override
    public Hospital getHospByHospCode(String hoscode) {
        return hospRepository.findByHoscode(hoscode);
    }
}
