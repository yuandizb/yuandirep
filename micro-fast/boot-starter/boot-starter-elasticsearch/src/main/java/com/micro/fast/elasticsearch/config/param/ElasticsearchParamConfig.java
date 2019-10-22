package com.micro.fast.elasticsearch.config.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * elasticsearch客户端配置参数类,用于接收从applcation.yml中加载的配置信息
 * @author lishouyu 18332763730@163.com 2017/09/24
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@AutoConfigureBefore(value = com.micro.fast.elasticsearch.config.ElasticsearchConfig.class)
@Validated
@Getter
@Setter
public class ElasticsearchParamConfig {

  /**
   * elasticsearch集群的ip:port数组,至少要有一个.
   */
  @NotNull
  private ArrayList<String> hosts;

  /**
   * elasticsearch客户端Settings类的配置参数 key:value数组的形式,至少要有一个.
   */
  @NotNull
  private ArrayList<String> settings;

}
