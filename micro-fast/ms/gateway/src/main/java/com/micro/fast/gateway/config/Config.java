package com.micro.fast.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@ConfigurationProperties(prefix = "login")
@Configuration
@Data
public class Config {
    /**
     * 不需要登录就能访问的路径
     */
    private ArrayList<String> notNeedLoginCheckPath;
}
