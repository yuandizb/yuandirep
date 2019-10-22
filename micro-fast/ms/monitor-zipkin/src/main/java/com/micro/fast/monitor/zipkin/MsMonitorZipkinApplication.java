package com.micro.fast.monitor.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * @author lsy
 * 集群间服务调用情况的聚合展示
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinStreamServer
public class MsMonitorZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMonitorZipkinApplication.class, args);
	}
}
