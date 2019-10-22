package com.micro.fast.ssm.sharding.config.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * mybatis配置参数
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
@Configuration
@AutoConfigureBefore(com.micro.fast.ssm.sharding.config.SqlSessionFactoryConfig.class)
@ConfigurationProperties(prefix = "mybatis")
@Validated
@Getter
@Setter
public class MybatisParamConfig {
  /**
   * pojo所在的包
   */
  @NotNull
  private String typeAliasesPackage;
  /**
   * 映射文件所在的文件路径
   */
  @NotNull
  private String mappers;
  /**
   * 分页插件的参数设置
   */
  private ArrayList<String> pageHelperProperties;
}
