package com.micro.fast.security.app;

import com.micro.fast.security.app.master.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自动配置入口类
 * @author lsy
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(SecurityProperties.class)
public class BootStarterSecurityOauthApplication {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
