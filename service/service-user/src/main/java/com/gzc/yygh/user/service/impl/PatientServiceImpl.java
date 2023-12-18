package com.gzc.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.client.DictFeignClient;
import com.gzc.yygh.common.utils.JwtHelper;
import com.gzc.yygh.model.user.Patient;
import com.gzc.yygh.user.mapper.PatientMapper;
import com.gzc.yygh.user.service.PatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 就诊人表 服务实现类
 * </p>
 *
 * @author gzc
 * @since 2023-12-18
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public List<Patient> findAll(String token) {
        Long userId = JwtHelper.getUserId(token);
        System.out.println("userId = " + userId);

        QueryWrapper<Patient> queryWrapper = new QueryWrapper<Patient>();
        queryWrapper.eq("user_id",userId);

        List<Patient> patients = baseMapper.selectList(queryWrapper);
        patients.stream().forEach(item->{
            this.packagePatient(item);
        });
        return patients;
    }

    private void packagePatient(Patient item){
        String nameByValue = dictFeignClient.getNameByValue(Long.parseLong(item.getCertificatesType()));
        item.getParam().put("certificatesTypeString",nameByValue);
    }
}
