package com.micro.fast.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 整个应用的服务网关，进行路由的规则的设置，登录以及权限的检验
 * @author lsy
 * @since 1.0
 * @version 1.0
 */
@SpringCloudApplication
//开启路径代理
@EnableZuulProxy
@EnableSwagger2
//服务调用
@EnableFeignClients
//熔断
@EnableHystrix
//对用熔断情况进行统计并展示
@EnableHystrixDashboard
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
