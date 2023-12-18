package com.gzc.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gzc.yygh.model.user.UserInfo;
import com.gzc.yygh.vo.user.LoginVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gzc
 * @since 2023-12-13
 */
public interface UserInfoService extends IService<UserInfo> {

    Map<String, Object> login(LoginVo loginVo);

    UserInfo getinfo(Long userId);

}
