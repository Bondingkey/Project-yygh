package com.gzc.yygh.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-cmn") //指定被调用方再注册中心的名字
public interface DictFeignClient {

    //根据省市区编号查询省市区
    @GetMapping(value = "/admin/cmn/{value}")
    public String getNameByValue(@PathVariable("value") Long value);

    //根据医院等级编号查询医院等级
    @GetMapping(value ="/admin/cmn/{code}/{value}")
    public String getNameByValueAndCode(@PathVariable("code") String code, @PathVariable("value") Long value);
}
