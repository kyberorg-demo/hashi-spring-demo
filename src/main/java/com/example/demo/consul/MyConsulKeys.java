package com.example.demo.consul;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@ConfigurationProperties("keys")
public @Data
class MyConsulKeys {
    private String key;
    private String secondKey;
}
