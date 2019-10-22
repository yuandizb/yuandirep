package com.micro.fast.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * elasticsearch的springboot热插拔模块启动类
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class BootStarterElasticsearchApplication {

	/**
	 * elasticsearch的springboot热插拔模块启动方法
	 * @param args 控制台参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(BootStarterElasticsearchApplication.class, args);
	}
}
