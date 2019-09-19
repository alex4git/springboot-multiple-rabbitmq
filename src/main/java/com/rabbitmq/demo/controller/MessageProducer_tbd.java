package com.rabbitmq.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.demo.config.AbstractMQConfig;
import com.rabbitmq.demo.mq.MultiMQTemplate;
//
//
//@RestController
//public class MessageProducer extends MultiMQTemplate{
//
//    @GetMapping("/send/{message}")
//    public String send(@PathVariable String message){
//    	for(int i=1;i< 100; i++) {
//    		try {
//				Thread.sleep(200);
//				System.out.println(i);
//				Map map = new HashMap<String,String>();
//				map.put("key", i);
//				firstMQTemplate.convertAndSend(AbstractMQConfig.EXCHANGE_TOPTIC_ROBOT_STATUS,AbstractMQConfig.ROUTINGKEY_ROBOT_STATUS + (i%2), map);//要发文本，不能直接发i，不然会报错failed: Could not decode a text frame as UTF-8
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//  
//
//    	}
//        return "success";
//    }
//}
