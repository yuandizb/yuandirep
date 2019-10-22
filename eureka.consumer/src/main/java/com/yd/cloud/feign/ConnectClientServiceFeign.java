package com.yd.cloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yd.cloud.cofig.FullLogConfiguration;

@Component
@FeignClient(name="FipGateway", configuration=FullLogConfiguration.class,fallback=ServiceManagerFallback.class)//EurekaGateway
public interface ConnectClientServiceFeign {
	//服务活跃检查接口
	@RequestMapping(value="/FileService/testPostWithSleep",method=RequestMethod.POST,produces = "application/json; charset=UTF-8")
	public  String sendClientService(@RequestBody String msg);
}

