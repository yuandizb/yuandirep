package com.yd.eureka.ctlserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netflix.discovery.shared.resolver.aws.AwsEndpoint;

@Component
public class IsolatedZoneConfig {
	@Value("${eureka.client.region}")
	private String region;

	@Value("${eureka.client.service-url.isolatedZone}")
	private String url;
	
	private List<AwsEndpoint> endpoints = new ArrayList<AwsEndpoint>();
	
	public List<AwsEndpoint> getAwsEndpoints() {
		if (endpoints.size() > 0 || url == null || url.trim().isEmpty()) {
			return endpoints;
		}
		
		String[] urls = url.split(",");
		
		for (String s : urls) {
			endpoints.add(new AwsEndpoint(s, region, "isolatedZone"));
		}
		
		return endpoints;
	}
}
