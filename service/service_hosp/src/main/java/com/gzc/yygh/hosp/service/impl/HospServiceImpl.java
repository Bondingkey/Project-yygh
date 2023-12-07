package com.gzc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.common.error.YyghError;
import com.gzc.yygh.hosp.mapper.HospitalSetMapper;
import com.gzc.yygh.hosp.repostitry.HospRepository;
import com.gzc.yygh.hosp.service.HospService;
import com.gzc.yygh.model.hosp.Hospital;
import com.gzc.yygh.model.hosp.HospitalSet;
import com.gzc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public Page getHospList(Integer pageNum, Integer pageSize, HospitalQueryVo hospitalQueryVo) {
        //查询信息
        Hospital hospital = new Hospital();
        if (!StringUtils.isEmpty(hospitalQueryVo.getHosname())){
            hospital.setHosname(hospitalQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hospitalQueryVo.getProvinceCode())){
            hospital.setProvinceCode(hospitalQueryVo.getProvinceCode());
        }
        if (!StringUtils.isEmpty(hospitalQueryVo.getCityCode())){
            hospital.setCityCode(hospitalQueryVo.getCityCode());
        }
        //设置模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                //.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withMatcher("hosname", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写

        Example<Hospital> of = Example.of(hospital,matcher);
        //分页信息
        PageRequest page = PageRequest.of(pageNum-1, pageSize, Sort.by("createTime").ascending());
        //需要查询信息和分页信息
        Page<Hospital> all = hospRepository.findAll(of, page);



        return all;
    }
}
