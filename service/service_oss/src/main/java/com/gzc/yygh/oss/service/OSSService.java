package com.gzc.yygh.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.gzc.yygh.oss.prop.OSSProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/17  09:23  周日
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Service
public class OSSService {

    @Autowired
    private OSSProperties ossProperties;

    public String upload(MultipartFile file) throws com.aliyuncs.exceptions.ClientException {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ossProperties.getEndpoint();
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossProperties.getBucketname();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,credentialsProvider);
        String fileName = new DateTime().toString("yyyy/MM/dd")+UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();

        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,fileName,file.getInputStream());
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            return "https://"+ossProperties.getBucketname()+"."+ossProperties.getEndpoint()+"/"+fileName;

        }catch(Exception ce) {
            System.out.println(ce.getMessage());
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
