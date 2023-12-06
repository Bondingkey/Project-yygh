package com.gzc.yygh.hosp.service;

import com.gzc.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospService {
    void insert(Map<String, Object> objectMap);

    String getSignByHoscode(String hoscode);

    Hospital getHospByHospCode(String hoscode);
}
