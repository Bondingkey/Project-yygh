package com.gzc.yygh.hosp.controller.admin;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.hosp.service.HospService;
import com.gzc.yygh.model.hosp.Hospital;
import com.gzc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/07  16:05  周四
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/admin/hospital")
//@CrossOrigin  //局部解决跨域问题
public class HospitalController {

    @Autowired
    private HospService hospService;

    //查看医院细节
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") String id){
        Hospital hospital = hospService.getdetail(id);
        return R.ok().data("hospital",hospital);
    }

    @PutMapping("/updateStatus/{id}/{status}")
    public R updateStatus(@PathVariable("id") String id,@PathVariable("status") Integer status){
        hospService.updateStatus(id,status);
        return R.ok();
    }

    @GetMapping("/getHospList/{pageNum}/{pageSize}")
    public R getHospList(@PathVariable("pageNum") Integer pageNum,
                         @PathVariable("pageSize") Integer pageSize,
                         HospitalQueryVo hospitalQueryVo){
        Page page = hospService.getHospList(pageNum,pageSize,hospitalQueryVo);

        return R.ok().data("total",page.getTotalElements()).data("list",page.getContent());
    }

}
