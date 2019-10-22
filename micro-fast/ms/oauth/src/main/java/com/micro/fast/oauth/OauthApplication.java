package com.micro.fast.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 前台用户认证中心
 * @author lsy
 */
@SpringCloudApplication
//服务调用
@EnableFeignClients
//熔断
@EnableHystrix
//对用熔断情况进行统计并展示
@EnableHystrixDashboard
@EnableSwagger2
public class OauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}
}
