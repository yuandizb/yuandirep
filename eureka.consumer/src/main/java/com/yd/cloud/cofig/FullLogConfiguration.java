package com.yd.cloud.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FullLogConfiguration {

	
	@Bean
	Logger.Level feignLevel(){
		return Logger.Level.FULL;
	}
}
