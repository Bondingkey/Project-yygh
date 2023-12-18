package com.gzc.yygh.user.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/15  10:15  周五
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Data
@Component
@ConfigurationProperties(prefix = "weixin")
public class WeixinProperties {

    private String appid;
    private String appscope;
    private String redirecturi;
}
