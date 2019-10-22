package com.micro.fast.upms;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lsy
 */
@Configuration
@ComponentScan
public class BootUpmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(BootUpmsApplication.class, args);
  }
}
