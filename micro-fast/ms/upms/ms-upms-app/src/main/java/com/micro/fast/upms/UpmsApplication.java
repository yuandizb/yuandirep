package com.micro.fast.upms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author lsy
 */
@SpringCloudApplication
@MapperScan(basePackages = {"com.micro.fast.upms.dao"})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
//服务调用
@EnableFeignClients
//熔断
@EnableHystrix
//对用熔断情况进行统计并展示
@EnableHystrixDashboard
public class UpmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(UpmsApplication.class, args);
  }
  /**
   * 密码加密的bean
   * @return
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

}
