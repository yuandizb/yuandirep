package com.micro.fast.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心启动类
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
/**
 * 开启配置中心相关服务
 */
@EnableConfigServer
/**
 * 允许注册到注册中心
 */
@EnableDiscoveryClient
public class ConfigServerApplication {

	/**
	 * 配置中心方法　
	 * @param args 控制台参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
