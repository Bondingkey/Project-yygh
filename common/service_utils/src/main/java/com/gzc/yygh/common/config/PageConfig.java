package com.gzc.yygh.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/28  10:50  周二
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: mp分页插件
 */
@SpringBootConfiguration
public class PageConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
