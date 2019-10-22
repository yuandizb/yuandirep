package com.micro.fast.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringCloudApplication
@MapperScan(basePackages = {"com.micro.fast.ucenter.dao"})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
//服务调用
@EnableFeignClients
//熔断
@EnableHystrix
//对用熔断情况进行统计并展示
@EnableHystrixDashboard
public class UcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcenterApplication.class, args);
	}
}
