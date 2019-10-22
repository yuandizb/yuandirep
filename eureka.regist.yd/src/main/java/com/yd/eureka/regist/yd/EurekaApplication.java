package com.yd.eureka.regist.yd;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication 
{
    public static void main( String[] args )
    {
    	new SpringApplicationBuilder(EurekaApplication.class).web(true).run(args);
    }
}
