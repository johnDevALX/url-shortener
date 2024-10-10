package com.ekene.serviceurl_shortener.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app")
public class AppConfig {
    private String redisConn;
    private String baseUrl;
}
