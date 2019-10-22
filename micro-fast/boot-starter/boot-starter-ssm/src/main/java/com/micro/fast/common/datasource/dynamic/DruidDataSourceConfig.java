package com.micro.fast.common.datasource.dynamic;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.micro.fast.common.datasource.dynamic.condition.MasterDataSourceCondition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lsy
 */
@Configuration
@AutoConfigureBefore(DynamicDataSourceConfig.class)
public class DruidDataSourceConfig {

  @Bean(name = DynamicDataSourceConfig.MASTER_DATA_SOURCE)
  @ConfigurationProperties("spring.datasource.druid.master")
  @Conditional(MasterDataSourceCondition.class)
  public DataSource masterDataSource(){
    return DruidDataSourceBuilder.create().build();
  }
}
