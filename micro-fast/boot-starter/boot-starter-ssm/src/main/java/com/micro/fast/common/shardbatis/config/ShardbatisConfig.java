package com.micro.fast.common.shardbatis.config;

import com.google.code.shardbatis.plugin.ShardPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * shardbatis的配置
 * @author lsy
 */
@Configuration
public class ShardbatisConfig {

  /**
   * shardPlugin的配置类,当这个配置属性不存在的时候分表插件不生效
   * @param xmlClassPath shard
   * @return
   */
  @Bean(name = "shardPlugin")
  @ConditionalOnProperty(prefix = "shardbatis",name = "configPath",matchIfMissing = false)
  public ShardPlugin shardPlugin(@Value("${shardbatis.configPath}") String xmlClassPath){
    ShardPlugin shardPlugin = new ShardPlugin();
    Properties properties = new Properties();
    properties.setProperty(ShardPlugin.SHARDING_CONFIG,xmlClassPath);
    shardPlugin.setProperties(properties);
    return shardPlugin;
  }
}
