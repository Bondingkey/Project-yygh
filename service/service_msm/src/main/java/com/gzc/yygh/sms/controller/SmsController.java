package com.gzc.yygh.sms.controller;

import com.gzc.yygh.common.result.R;
import com.gzc.yygh.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/14  09:22  周四
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send/{phone}")
    public R sendCode(@PathVariable("phone") String phone){
        boolean flag = smsService.sendCode(phone);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}
