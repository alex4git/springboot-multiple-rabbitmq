package com.rabbitmq.demo.controller;

import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.stereotype.Component;

import com.rabbitmq.demo.mq.MultiMQTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MsgSender extends MultiMQTemplate implements ConfirmCallback {

	public MsgSender(RabbitTemplate firstMQTemplate, RabbitTemplate secondMQTemplate) {
		super(firstMQTemplate, secondMQTemplate);
		firstMQTemplate.setConfirmCallback(this);
		secondMQTemplate.setConfirmCallback(this);
	}

	public void sendTopicMsg(RabbitTemplate template, String topicExchange, String routingKey, String content) {
		CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
		// 把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
		template.convertAndSend(topicExchange, routingKey, content, correlationId);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println(" 回调id:" + correlationData);
		if (ack) {
			System.out.println("消息成功消费");
		} else {
			System.out.println("消息消费失败:" + cause);
		}
	}

}
