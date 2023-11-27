package com.gzc.yygh.common.config;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/27  15:13  周一
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@SpringBootConfiguration
@EnableSwagger2 //添加Swagger2支持
public class Swagger2Config {
    /*
    Swagger2步骤:
    1,添加依赖
    2,编写一个配置类,标注@EnableSwagger2用来添加Swagger2支持
    3,在配置类中编写一个返回Docket的方法,并在改方法中设置一系列属性配置,一个这样的方法对应一个分组
    4,将方法添加@Bean标签放入Spring容器
    5,在想要使用Swagger2的微服务的启动类上添加@ComponentScan("com.gzc.yygh")注解,指定微服务和配置类的所在目录的相同部分
     */

    //提供一个Docket对象
    @Bean
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")//设置分组的名字
                .apiInfo(new ApiInfoBuilder()
                        .title("管理员系统使用")
                        .description("预约挂号平台专用Swagger2接口文档")
                        .contact(new Contact(
                                "管志成",
                                "http://localhost:8201/admin/hosp/hospitalSet/findAll",
                                "gzc2409246291@gmail.com"))
                        .build())//设置详细描述信息
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))//设置筛选条件
                .build();
    }

    @Bean
    public Docket getApiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")//设置分组的名字
                .apiInfo(new ApiInfoBuilder()
                        .title("第三方医院对接使用")
                        .description("预约挂号平台第三方医院专用Swagger2接口文档")
                        .contact(new Contact(
                                "管志成",
                                "http://localhost:8201/admin/hosp/hospitalSet/findAll",
                                "gzc2409246291@gmail.com"))
                        .build())//设置详细描述信息
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))//设置筛选条件
                .build();
    }
}
