package com.pang.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AilOSSProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucketName;
}
