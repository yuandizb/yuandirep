package com.micro.fast.fastdfs.config.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * fastdfs java client properties auto config class
 * @author lsy
 * @version 1.0
 * @since 1.0
 */
@Configuration
@Validated
@ConfigurationProperties(prefix = "fastdfs")
@AutoConfigureBefore({com.micro.fast.fastdfs.config.FastDFSClientConfig.class})
@Setter
@Getter
public class FastDFSClientParamConfig {

  private String connectTimeoutInSeconds;

  private String networkTimeoutInSeconds;

  private String charset;

  private String httpAntiStealToken;

  private String httpSecretKey;

  private String httpTrackerHttpPort;

  @NotNull
  private String trackerServers;

}
