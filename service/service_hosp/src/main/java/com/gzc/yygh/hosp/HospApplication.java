package com.gzc.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/27  10:12  周一
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 微服务启动类
 */
@SpringBootApplication
@MapperScan("com.gzc.yygh.hosp.mapper")//mp必用的注解
@ComponentScan("com.gzc.yygh")//扫描到所依赖的模块中的对象,那个微服务想用Swagger那个微服务启动类就加上
@EnableDiscoveryClient //nacos必用注解
@EnableFeignClients(basePackages = "com.gzc.yygh")   //openFegin的必用注解
public class HospApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospApplication.class,args);
    }
}
