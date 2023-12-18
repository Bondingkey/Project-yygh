package com.gzc.yygh.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gzc.yygh.model.user.Patient;

import java.util.List;

/**
 * <p>
 * 就诊人表 服务类
 * </p>
 *
 * @author gzc
 * @since 2023-12-18
 */
public interface PatientService extends IService<Patient> {

    List<Patient> findAll(String token);

}
