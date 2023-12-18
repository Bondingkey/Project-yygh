package com.gzc.yygh.hosp.controller.user;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.hosp.service.HospService;
import com.gzc.yygh.model.hosp.Hospital;
import com.gzc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/12  08:58  周二
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/user/hosp/hospital")
//@CrossOrigin  //局部解决跨域问题
public class UserHospitalController {

    @Autowired
    private HospService hospService;

    @GetMapping("/list")
    public R getHospitalList(HospitalQueryVo hospitalQueryVo){
        Page<Hospital> hospList = hospService.getHospList(1, 1000000, hospitalQueryVo);
        return R.ok().data("list",hospList.getContent());
    }

    @GetMapping("/{name}")
    public R findByName(@PathVariable("name") String name){
        List<Hospital> hospitalList = hospService.findByNameLike(name);
        return R.ok().data("list",hospitalList);
    }

    @GetMapping("/detail/{hoscode}")
    public R detailHosp(@PathVariable("hoscode") String hoscode){
        Hospital hospital =hospService.getHospDetail(hoscode);
        return R.ok().data("hospital",hospital);
    }

}
