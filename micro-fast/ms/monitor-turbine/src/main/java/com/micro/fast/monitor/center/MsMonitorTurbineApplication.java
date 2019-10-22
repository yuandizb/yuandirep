package com.micro.fast.monitor.center;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author lsy
 */
@SpringCloudApplication
//集群熔断降级情况的聚合展示
@EnableTurbine
@EnableHystrixDashboard
@EnableHystrix
@EnableAdminServer
public class MsMonitorTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMonitorTurbineApplication.class, args);
	}
}
