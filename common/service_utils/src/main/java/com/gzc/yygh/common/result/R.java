package com.gzc.yygh.common.result;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/28  09:47  周二
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 编写一个统一结果返回类方便前端接收数据
 */
@Getter
@ToString
public class R {

    private Integer code;
    private String message;
    private Boolean success;
    private Map<String, Object> data = new HashMap<String,Object>();

    //私有化构造器使外界无法直接造对象
    private R(){}

    //返回成功格式的方法
    public static R ok(){
        R r = new R();
        r.code=REnum.SUCCESS.getCode();
        r.success=REnum.SUCCESS.getSuccess();
        r.message=REnum.SUCCESS.getMessage();
        return r;
    }

    //返回失败格式的方法
    public static R error(){
        R r = new R();
        r.code=REnum.ERROR.getCode();
        r.success=REnum.ERROR.getSuccess();
        r.message=REnum.ERROR.getMessage();
        return r;
    }

    //返回自定义返回码的方法
    public R code(Integer code){
        this.code= code;
        return this;
    }
    //返回自定义消息码的方法
    public R message(String message){
        this.message= message;
        return this;
    }
    //返回自定义成功码的方法
    public R success(Boolean success){
        this.success= success;
        return this;
    }
    //返回自定义数据码的方法
    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    //返回自定义数据码的方法
    public R data(Map<String,Object> data){
        this.data=data;
        return this;
    }
}
