package com.lzh.lzhapiclientsdk;

import com.lzh.lzhapiclientsdk.client.LzhApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//可以读取到我们的application文件中的配置,把读取到的配置设置到属性中
@ConfigurationProperties("lzh-client")
@Data
@ComponentScan
public class LzhApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public LzhApiClient LzhApiClient(){
        return new LzhApiClient(accessKey,secretKey);
    }
}
