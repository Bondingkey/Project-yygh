package com.gzc.yygh.common.handler;

import com.gzc.yygh.common.error.YyghError;
import com.gzc.yygh.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/28  15:20  周二
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 全局异常处理类
 */
@RestControllerAdvice  //表示一个全局异常处理类
@Slf4j //使用@Slf4j注解来标明日志的级别,用来主动获取日志信息并记录
public class GlobalExceptionHandler {
    /*
    步骤:
    1,新建一个全局异常处理类并表明一个注解@RestControllerAdvice
    2,设置一个方法,用来处理异常,并在@ExceptionHandler(Exception.class)注解中指明要进行处理的异常类型
    3,当程序出现此类型异常时,会执行方法体的内容
     */

    @ExceptionHandler(Exception.class)//填写要进行处理的异常类型
    public R errorhandler(Exception exception){
        exception.printStackTrace();//暂时打印到控制台
        log.error(exception.getMessage());  //记录一个error的日志放入文件系统中
        return R.error().message(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)//填写要进行处理的异常类型
    public R errorhandler(RuntimeException RuntimeException){
        RuntimeException.printStackTrace();//暂时打印到控制台
        log.error(RuntimeException.getMessage());  //记录一个error的日志放入文件系统中
        return R.error().message(RuntimeException.getMessage());
    }

    @ExceptionHandler(YyghError.class)//填写要进行处理的异常类型
    public R errorhandler(YyghError yyghError){
        yyghError.printStackTrace();//暂时打印到控制台
        log.error(yyghError.getMessage());  //记录一个error的日志放入文件系统中
        return R.error().message(yyghError.getMessage());
    }
}
