package com.gzc.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.common.error.YyghError;
import com.gzc.yygh.common.utils.JwtHelper;
import com.gzc.yygh.enums.AuthStatusEnum;
import com.gzc.yygh.model.user.UserInfo;
import com.gzc.yygh.user.mapper.UserInfoMapper;
import com.gzc.yygh.user.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzc.yygh.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gzc
 * @since 2023-12-13
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Map<String, Object> login(LoginVo loginVo) {
        //先获取用户输入的手机号和验证码
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();
        //验空
        if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(code)){
            throw new YyghError("您输入的手机号和验证码错误",20001);
        }
        //验证验证码
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isEmpty(redisCode) || !redisCode.equals(code)){
            throw new YyghError("验证码错误",20001);
        }

        String openid = loginVo.getOpenid();
        UserInfo userInfo = null;
        if (StringUtils.isEmpty(openid)){
            //判断首次登录
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>();
            queryWrapper.eq("phone",phone);
            userInfo = baseMapper.selectOne(queryWrapper);
            if (userInfo == null){
                userInfo = new UserInfo();
                userInfo.setPhone(phone);
                baseMapper.insert(userInfo);
                userInfo.setStatus(1);
            }
        }else {
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>();
            queryWrapper.eq("openid",openid);
            userInfo = baseMapper.selectOne(queryWrapper);

            QueryWrapper<UserInfo> phoneWrapper = new QueryWrapper<UserInfo>();
            phoneWrapper.eq("phone", phone);
            UserInfo userInfo2 = baseMapper.selectOne(phoneWrapper);

            if (userInfo2 == null){
                userInfo.setPhone(phone);
                //userInfo.setStatus(1);
                baseMapper.updateById(userInfo);
            }else {
                userInfo2.setOpenid(userInfo.getOpenid());
                userInfo2.setNickName(userInfo.getNickName());

                baseMapper.updateById(userInfo2);
                baseMapper.deleteById(userInfo.getId());
            }
        }

        //验证状态
        if (userInfo.getStatus() == 0){
            throw new YyghError("用户锁定中",20001);
        }

        //返回页面显示名称
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);

        String token = JwtHelper.createToken(userInfo.getId(), name);

        map.put("token",token);
        return map;
    }

    @Override
    public UserInfo getinfo(Long userId) {
        UserInfo userInfo = baseMapper.selectById(userId);
        userInfo.getParam().put("authStatusString", AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus()));
        return userInfo;
    }
}
