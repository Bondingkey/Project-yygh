package com.gzc.yygh.gateway.filter;

import com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.print.DocFlavor;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/09  15:41  周六
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //对于登录接口的请求就不要拦截了
        if (antPathMatcher.match("/admin/user/**",path)){
            return chain.filter(exchange);
        }else {//对于非登录接口必须登录
            List<String> strings = request.getHeaders().get("X-Token");
            if (strings == null){
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set(HttpHeaders.LOCATION,"http://localhost:9528/");
                return response.setComplete(); //结束请求
            }else {
                return chain.filter(exchange);
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
