package com.yd.cloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yd.cloud.feign.ConnectClientServiceFeign;

@Controller
public class ConsumerController {
	static Logger logger=LoggerFactory.getLogger(ConsumerController.class);
	@Autowired
	ConnectClientServiceFeign clientServiceFeign;
	
	@RequestMapping(value="/request-client",method=RequestMethod.GET)
	public  String sendRequestClient(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("sendID", "00000");
		jsonObject.put("msg", "来自消费方的请求信息");
		try {
			jsonObject.put("result", "请求成功");
			String result=clientServiceFeign.sendClientService(jsonObject.toString());	
//			String result=refreshPostCall(jsonObject.toJSONString());
			logger.info(result);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "调用feign请求失败!!");
			logger.info("调用feign失败",e);
			return jsonObject.toJSONString();
		}
		return jsonObject.toJSONString();
	}
	public static String refreshPostCall(String sendMsg) {

		String url = "http://"+"20.2.0.163:5003"+"/EurekaClientService/recv-request";
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();

//		acceptableMediaTypes.add(e);
		// header
		HttpHeaders header = new HttpHeaders();
		header.setAccept(acceptableMediaTypes);
		header.set("Content-type", "text/html;charset=utf-8");
		header.set("Content-type", "application/json;charset=UTF-8");
		HttpEntity<String> requestEntity = new HttpEntity<String>(sendMsg, header);
		
		RestTemplate restTemplate = new RestTemplate();
		System.out.println(url);
		ResponseEntity<String> resp = restTemplate.postForEntity(url,requestEntity, String.class);
	
		String respMsg = resp.getBody();
		return respMsg;
	}

}
