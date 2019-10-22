
package com.micro.fast.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 注册中心启动类
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaServer //开启注册中心相关服务,默认配置注册中心会注册自己
@EnableSwagger2
public class EurekaServerApplication {
    /**
     * 注册中心方法
     * @param args 控制台参数
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
