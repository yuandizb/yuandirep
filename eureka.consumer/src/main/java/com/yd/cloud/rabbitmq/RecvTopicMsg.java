package com.yd.cloud.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.yd.cloud.utils.ConnectionRabbit;
/**
 * 从rabbitMQ接收消息
 * @author yuandi
 *
 */
@Component
public class RecvTopicMsg {
	static Logger logger=LoggerFactory.getLogger(RecvTopicMsg.class);
	private final static String queue_name="yuandi-test-1";
	private final static String exchange_name="yuandi-exchange-test2";
	public  void recvMsg() {
		//建立连接
		Connection conn=ConnectionRabbit.getConnection();
		
		if(conn!=null){
			//获取通道
			Channel channel=null;
			try {
				channel=conn.createChannel();
				  /*
		         * 声明（创建）队列
		         * 参数1：队列名称
		         * 参数2：为true时server重启队列不会消失(持久化)
		         * 参数3：队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
		         * 参数4：队列不再使用时是否自动删除（没有连接，并且没有未处理的消息)
		         * 参数5：建立队列时的其他参数
		         */
				channel.queueDeclare(queue_name, true, false, false, null);
				/**
				 * 绑定队列和交换机
				 * 
				 */
				channel.queueBind(queue_name, exchange_name, "yuandi");
				//定义消费者
				DefaultConsumer consumer= new DefaultConsumer(channel){
					@Override
					public void handleDelivery(String consumerTag,
							Envelope envelope, BasicProperties properties,
							byte[] body) throws IOException {
						// TODO Auto-generated method stub
						String msg=new String(body,"utf-8");
						if(msg.length()>0){
							logger.info("receive msg"+msg);
							logger.info("recv msg success!");
						}
					}
				};
				//关闭自动应答
				boolean autoAck=false;
				//监听队列
				channel.basicConsume(queue_name, autoAck, consumer);
				try {
					channel.close();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn.close();
			} catch (IOException e) {
				logger.error("获取通道异常",e);
			}
		}else{
			logger.error("连接rabbitMQ异常");
		}
	}

}
