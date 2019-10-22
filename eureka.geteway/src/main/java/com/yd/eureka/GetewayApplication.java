package com.yd.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointMBeanExportAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude= {JmxAutoConfiguration.class, EndpointMBeanExportAutoConfiguration.class})
@EnableZuulProxy
@ComponentScan("com.yd.pip.ctlserver")
@EnableFeignClients
public class GetewayApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(GetewayApplication.class, args);
    }
}
