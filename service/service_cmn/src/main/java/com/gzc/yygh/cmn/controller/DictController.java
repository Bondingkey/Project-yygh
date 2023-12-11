package com.gzc.yygh.cmn.controller;

import com.gzc.yygh.cmn.service.DictService;
import com.gzc.yygh.common.result.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author gzc
 * @since 2023-12-02
 */
@RestController
@RequestMapping("/admin/cmn")
public class DictController {

    @Autowired
    private DictService dictService;

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        dictService.upload(file);
        return R.ok();
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        dictService.download(response);
    }

    @ApiOperation("根据父id查询子元素")
    @GetMapping("/getChildByPid/{pid}")
    public R  getChildListByPid(@PathVariable("pid") Long pid){
        List list =  dictService.getChildListByPid(pid);
        return R.ok().data("items",list);
    }

    //根据省市区编号查询省市区
    @GetMapping("/{value}")
    public String getNameByValue(@PathVariable("value") Long value){
        return dictService.getNameByValue(value);
    }

    //根据医院等级编号查询医院等级
    @GetMapping("/{code}/{value}")
    public String getNameByValueAndCode(@PathVariable("code") String code,
                                        @PathVariable("value") Long value){
        return dictService.getNameByValueAndCode(code,value);
    }
}
