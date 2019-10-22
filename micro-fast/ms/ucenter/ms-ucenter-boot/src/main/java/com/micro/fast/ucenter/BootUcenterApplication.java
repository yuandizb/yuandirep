package com.micro.fast.ucenter;


import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BootUcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootUcenterApplication.class, args);
	}
}
