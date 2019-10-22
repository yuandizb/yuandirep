package com.yd.cloud.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 连接rabbitMQ工具类
 * @author yuandi
 *
 */
public class ConnectionRabbit {
   static Logger logger=LoggerFactory.getLogger(ConnectionRabbit.class);
	public static Connection getConnection(){
		Connection conn=null;
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		factory.setUsername("yuandi");
		factory.setPassword("yuandi");
		try {
			conn=factory.newConnection();
			return conn;
		} catch (IOException e) {
			logger.error("和rabbitmq建立连接错误",e);
			return null;
		} catch (TimeoutException e) {
			logger.error("和rabbitmq建立连超时",e);
			return null;
		}
	}

}
