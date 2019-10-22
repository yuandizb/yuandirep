package com.micro.fast.common.id;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式主键生成
 * @author lsy
 */
@Configuration
public class IdConfig {

    @Bean
    @ConditionalOnProperty(prefix = "snowflake",value = "use",havingValue = "true" ,matchIfMissing = false)
    public SnowflakeId snowflakeId(@Value("${snowflake.workId}") Long workId){
        return new SnowflakeId(workId);
    }
}
