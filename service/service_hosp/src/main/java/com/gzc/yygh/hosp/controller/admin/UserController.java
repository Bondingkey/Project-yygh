package com.gzc.yygh.hosp.controller.admin;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.model.acl.User;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/30  10:57  周四
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/admin/user")
//@CrossOrigin //浏览器使用axios调用服务器时会出现跨区问题,使用此注解可以局部解决(仅解决当前Controller)
public class UserController {

    //登录接口
    @PostMapping("/login")
    //由于前端发过来的请求是json格式的所以需要加RequestBody注解,@RequestBody不能使用Get请求,但是能使用Post请求
    public R login(@RequestBody User user){
        //暂时先不用从数据库中查询
        return R.ok().data("token","admin-token");
    }

    //展示用户信息接口
    //由于前端发过来的是get请求所以要用get请求GetMapping
    @GetMapping("/info")
    //由于前端使用的是get请求中的?xxx=xxx传的参数可以直接在方法参数获取,要求变量需要与前端传过来的key值一致,不一致可以使用@RequestParam指定
    public R info(String token){
        //放入数据
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("roles","[admin]");
        stringObjectHashMap.put("introduction","I am a super administrator");
        stringObjectHashMap.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        stringObjectHashMap.put("name","Super Admin");

        //暂时先不用从数据库中查询
        return R.ok().data(stringObjectHashMap);
    }
}
