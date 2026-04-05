package com.pang.Utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
public class AliOSSUtils {

    @Resource
    private AilOSSProperties ailOSSProperties;

    /**
     * 实现图片上传功能
     */
    public String upload(MultipartFile multipartFile) throws IOException{
        String accessKeyId = ailOSSProperties.getAccessKeyId();
        String bucketName = ailOSSProperties.getBucketName();
        String accessKeySecret = ailOSSProperties.getAccessKeySecret();
        String endpoint = ailOSSProperties.getEndpoint();

        log.info("OSS Config: id={}, secret={}, endpoint={}, bucket={}", accessKeyId, accessKeySecret, endpoint, bucketName);

        //获取输入流
        InputStream inputStream = multipartFile.getInputStream();

        //避免文件被覆盖，所以使用uuid
        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));

        //上传文件到云端
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject(bucketName,fileName,inputStream);

        //文件上传访问路径（也就是浏览器渲染图片的地址）
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;

        ossClient.shutdown();
        return url;

    }
}
