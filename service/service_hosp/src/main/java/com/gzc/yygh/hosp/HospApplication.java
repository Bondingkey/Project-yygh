package com.gzc.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/27  10:12  周一
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 微服务启动类
 */
@SpringBootApplication
@MapperScan("com.gzc.yygh.hosp.mapper")
@ComponentScan("com.gzc.yygh")//扫描到所依赖的模块中的对象,那个微服务想用Swagger那个微服务启动类就加上
public class HospApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospApplication.class,args);
    }
}
