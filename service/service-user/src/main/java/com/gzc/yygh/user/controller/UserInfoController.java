package com.gzc.yygh.user.controller;


import com.gzc.yygh.common.result.R;
import com.gzc.yygh.common.utils.JwtHelper;
import com.gzc.yygh.enums.AuthStatusEnum;
import com.gzc.yygh.model.user.UserInfo;
import com.gzc.yygh.user.service.UserInfoService;
import com.gzc.yygh.vo.user.LoginVo;
import com.gzc.yygh.vo.user.UserAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gzc
 * @since 2023-12-13
 */
@RestController
@RequestMapping("/user/userinfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PutMapping("/update")
    public R save(@RequestHeader String token, UserAuthVo userAuthVo){
        Long userId = JwtHelper.getUserId(token);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setName(userAuthVo.getName());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());

        userInfoService.updateById(userInfo);
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        Map<String,Object> map  =  userInfoService.login(loginVo);
        return  R.ok().data(map);
    }

    @GetMapping("/info")
    public R info(@RequestHeader String token){
        Long userId = JwtHelper.getUserId(token);
        UserInfo userInfo = userInfoService.getinfo(userId);
        return  R.ok().data("user",userInfo);
    }

}

