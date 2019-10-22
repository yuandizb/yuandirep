package com.micro.fast.common.init.config;

import com.micro.fast.common.init.properties.SqlInitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sql初始化配置类
 * @author lsy
 */
@Configuration
public class InitConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    @ConditionalOnProperty("spring.datasource.druid.sql-path")
    public SqlInitProperties dataSourceInitProperties(){
        return new SqlInitProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    @ConditionalOnProperty("spring.datasource.druid.master.sql-path")
    public SqlInitProperties masterDataSourceInitProperties(){
        return new SqlInitProperties();
    }
}
