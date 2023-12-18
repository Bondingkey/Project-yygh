package com.gzc.yygh.oss.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/17  09:12  周日
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class OSSProperties {

    private String bucketname;
    private String endpoint;
    private String keyid;
    private String keysecret;


}
