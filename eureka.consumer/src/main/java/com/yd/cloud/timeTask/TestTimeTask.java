package com.yd.cloud.timeTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yd.cloud.controller.ConsumerController;
import com.yd.cloud.rabbitmq.RecvMsg;
import com.yd.cloud.rabbitmq.RecvTopicMsg;

@Component
public class TestTimeTask {

	@Autowired
	ConsumerController consumerController;
	@Autowired
	RecvMsg recv;
	@Autowired
	RecvTopicMsg topic;
/*	@Scheduled(cron = "0/10 * * * * ?")// 每1min执行
	public void testTime() {
		consumerController.sendRequestClient();
		
		}*/
	@Scheduled(cron="0/1 * * * * ?")
	public void recvMsg(){
		topic.recvMsg();
	}
}
