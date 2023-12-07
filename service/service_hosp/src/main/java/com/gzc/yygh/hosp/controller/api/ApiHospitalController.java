package com.gzc.yygh.hosp.controller.api;

import com.gzc.yygh.common.error.YyghError;
import com.gzc.yygh.common.utils.MD5;
import com.gzc.yygh.hosp.Result.Result;
import com.gzc.yygh.hosp.service.HospService;
import com.gzc.yygh.hosp.utils.HospUtils;
import com.gzc.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/06  09:37  周三
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiHospitalController {

    @Autowired
    private HospService hospService;


    @PostMapping("/hospital/show")
    public Result<Hospital> getHospInfo(HttpServletRequest request){
        Map<String, Object> objectMap = HospUtils.switchMap(request.getParameterMap());
        String hoscode = (String) objectMap.get("hoscode");
        Hospital hospital = hospService.getHospByHospCode(hoscode);
        return Result.ok(hospital);
    }

    //添加医院信息
    @PostMapping("/saveHospital")
    public Result saveHospital(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        //转化map
        Map<String, Object> objectMap = HospUtils.switchMap(parameterMap);
        //验证signKey信息
        //传过来的加密后的signKey
        String sign = (String)objectMap.get("sign");
        String hoscode = (String)objectMap.get("hoscode");
        String Mysign = hospService.getSignByHoscode(hoscode);
        String encrypt = MD5.encrypt(Mysign);

        if (!StringUtils.isEmpty(sign) && !StringUtils.isEmpty(encrypt) && encrypt.equals(sign)){
            String logoData = (String) objectMap.get("logoData");
            String result = logoData.replaceAll(" ", "+");
            objectMap.put("logoData",result);
            //保存
            hospService.insert(objectMap);
            return Result.ok();
        }else {
            throw new YyghError("保存失败",20001);
        }
    }
}
