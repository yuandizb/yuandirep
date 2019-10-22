package com.yd.cloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import feign.Logger;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@EnableEurekaClient
@ComponentScan("com.yd.cloud")
@EnableFeignClients
@EnableScheduling
public class ConsumerApplication 
{
	
    public static void main( String[] args )
    {
    	SpringApplication.run(ConsumerApplication.class, args);
        
    }
}
