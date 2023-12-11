package com.gzc.yygh.hosp.service;

import com.gzc.yygh.model.hosp.Hospital;
import com.gzc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospService {
    void insert(Map<String, Object> objectMap);

    String getSignByHoscode(String hoscode);

    Hospital getHospByHospCode(String hoscode);

    Page getHospList(Integer pageNum, Integer pageSize, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Hospital getdetail(String id);

}
