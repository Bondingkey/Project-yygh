package com.gzc.yygh.hosp.service;

import com.gzc.yygh.model.hosp.Department;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface DepartmentService {
    void saveDepartment(Map<String, Object> objectMap);

    Page<Department> getDepartmentPage(Map<String, Object> objectMap);

}
