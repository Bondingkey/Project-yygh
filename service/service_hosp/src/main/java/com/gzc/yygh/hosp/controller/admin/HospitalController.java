package com.gzc.yygh.hosp.controller.admin;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.hosp.service.HospService;
import com.gzc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/07  16:05  周四
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/admin/hospital")
public class HospitalController {

    @Autowired
    private HospService hospService;

    @GetMapping("/getHospList/{pageNum}/{pageSize}")
    public R getHospList(@PathVariable("pageNum") Integer pageNum,
                         @PathVariable("pageSize") Integer pageSize,
                         HospitalQueryVo hospitalQueryVo){
        Page page = hospService.getHospList(pageNum,pageSize,hospitalQueryVo);

        return R.ok().data("total",page.getTotalElements()).data("list",page.getContent());
    }

}
