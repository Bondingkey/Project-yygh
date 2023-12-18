package com.gzc.yygh.oss.controller;

import com.aliyuncs.exceptions.ClientException;
import com.gzc.yygh.common.result.R;
import com.gzc.yygh.oss.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/17  09:23  周日
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RestController
@RequestMapping("/user/oss/file")
public class OSSController {

    @Autowired
    private OSSService ossService;

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws ClientException {
        String url = ossService.upload(file);
        return R.ok().data("url",url);
    }
}
