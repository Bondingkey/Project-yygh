package com.gzc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzc.yygh.hosp.repostitry.DepartmentRepository;
import com.gzc.yygh.hosp.service.DepartmentService;
import com.gzc.yygh.model.hosp.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/06  16:07  周三
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartment(Map<String, Object> objectMap) {
        Department department = JSONObject.parseObject(JSONObject.toJSONString(objectMap), Department.class);
        String hoscode = department.getHoscode();
        String depcode = department.getDepcode();
        Department Mydepartment = departmentRepository.findByHoscodeAndDepcode(hoscode,depcode);

        if (Mydepartment==null){
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }else {
            department.setCreateTime(Mydepartment.getCreateTime());
            department.setUpdateTime(new Date());
            department.setIsDeleted(Mydepartment.getIsDeleted());
            department.setId(Mydepartment.getId());
            departmentRepository.save(department);
        }

    }

    @Override
    public Page<Department> getDepartmentPage(Map<String, Object> objectMap) {
        Integer page = Integer.parseInt((String) objectMap.get("page"));
        Integer limit = Integer.parseInt((String) objectMap.get("limit"));

        String hoscode = (String) objectMap.get("hoscode");

        Department department = new Department();
        department.setHoscode(hoscode);
        Example<Department> example = Example.of(department);

        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Department> all = departmentRepository.findAll(example,pageable);

        return all;
    }

    @Override
    public void remove(Map<String, Object> objectMap) {
        String hoscode = (String) objectMap.get("hoscode");
        String depcode = (String) objectMap.get("depcode");

        //先查询后删除
        Department byHoscodeAndDepcode = departmentRepository.findByHoscodeAndDepcode(hoscode, depcode);
        departmentRepository.deleteById(byHoscodeAndDepcode.getId());

    }
}
