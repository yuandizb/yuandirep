package com.yd.cloud.feign;

/**
 * 服务熔断
 */

import org.springframework.stereotype.Component;

@Component
public class ServiceManagerFallback implements ConnectClientServiceFeign {

	@Override
	public String sendClientService(String msg) {
		// TODO Auto-generated method stub
		return "调用失败";
	}
	
	
	



}
