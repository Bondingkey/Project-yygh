package com.gzc.yygh.common.error;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/28  15:45  周二
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 自定义异常类
 */
public class YyghError extends RuntimeException{

    private String message;
    private Integer code;

    public YyghError(String message,Integer code){
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
