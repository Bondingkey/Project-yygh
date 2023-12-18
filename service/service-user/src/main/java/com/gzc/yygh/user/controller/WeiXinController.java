package com.gzc.yygh.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.common.error.YyghError;
import com.gzc.yygh.common.result.R;
import com.gzc.yygh.common.utils.JwtHelper;
import com.gzc.yygh.model.user.UserInfo;
import com.gzc.yygh.user.prop.WeixinProperties;
import com.gzc.yygh.user.service.UserInfoService;
import com.gzc.yygh.user.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/15  10:01  周五
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Controller
@RequestMapping("/user/userinfo/wx")
public class WeiXinController {

    @Autowired
    private WeixinProperties weixinProperties;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/params")
    @ResponseBody
    public R weixin() throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(weixinProperties.getRedirecturi(), "UTF-8");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("appid",weixinProperties.getAppid());
        map.put("scope","snsapi_login");
        map.put("redirecturl",encode);
        map.put("state",System.currentTimeMillis()+"");

        return R.ok().data(map);
    }

    @GetMapping("/callback")
    public String callback(String code,String state) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder append = stringBuilder.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");
        String format = String.format(append.toString(), weixinProperties.getAppid(), weixinProperties.getAppscope(), code);

        String result = HttpClientUtils.get(format);
        JSONObject jsonObject = JSONObject.parseObject(result);
        //访问微信服务器的凭证
        String access_token = jsonObject.getString("access_token");
        //微信扫描用户在微信服务器的唯一标识符
        String openid = jsonObject.getString("openid");

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>();
        queryWrapper.eq("openid",openid);
        UserInfo userInfo = userInfoService.getOne(queryWrapper);

        if (userInfo == null){
            //首次登录,存入表中
            userInfo = new UserInfo();
            userInfo.setOpenid(openid);
            //给微信服务器发送请求获取用户信息
            StringBuilder sb = new StringBuilder();
            StringBuilder append1 = sb.append("https://api.weixin.qq.com/sns/userinfo")
                    .append("?access_token=%s")
                    .append("&openid=%s");

            String format1 = String.format(append1.toString(), access_token, openid);
            String result1 = HttpClientUtils.get(format1);
            JSONObject jsonObject1 = JSONObject.parseObject(result1);
            String nickname = jsonObject1.getString("nickname");
            userInfo.setNickName(nickname);
            userInfo.setStatus(1);

            userInfoService.save(userInfo);
        }

        //跑到这里userInfo一定不为空
        //验证状态
        if (userInfo.getStatus() == 0){
            throw new YyghError("用户锁定中",20001);
        }

        //返回用户信息
        Map<String,String> map = new HashMap<String,String>();

        //检查手机号是否为空,如果为空,强制绑定手机号
        if (StringUtils.isEmpty(userInfo.getPhone())){
            map.put("openid",openid);
        }else {
            map.put("openid","");
        }

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

        return "redirect:http://localhost:3000/weixin/callback?token="+map.get("token")+ "&openid="+map.get("openid")+"&name="+URLEncoder.encode(map.get("name"),"utf-8");
    }
}
