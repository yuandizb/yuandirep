package com.yd.eureka.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(SpringUtil.class);
	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context) {
		SpringUtil.context = context;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		try {
			if (context != null) {
				return context.getBean(clazz);
			}
			logger.error("ApplicationContext 对象没有设置，无法获取Bean");
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public static <T> T getBean(String name, Class<T> clazz) {
		try {
			if (context != null) {
				return context.getBean(name, clazz);
			}
			logger.error("ApplicationContext 对象没有设置，无法获取Bean");
			
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public static String getApplicationName() {
		return context.getApplicationName();
	}

}
