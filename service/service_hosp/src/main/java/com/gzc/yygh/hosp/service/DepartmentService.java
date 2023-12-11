package com.gzc.yygh.hosp.service;

import com.gzc.yygh.model.hosp.Department;
import com.gzc.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    void saveDepartment(Map<String, Object> objectMap);

    Page<Department> getDepartmentPage(Map<String, Object> objectMap);

    void remove(Map<String, Object> objectMap);

    List<DepartmentVo> findAllDept(String hoscode);

    String getDepName(String hoscode, String depcode);
}
