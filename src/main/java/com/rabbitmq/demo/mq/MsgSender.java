package com.rabbitmq.demo.mq;

import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MsgSender implements ConfirmCallback {
	
	//由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入?
	//如果需要使用secondMQTemplate，这里就配置secondMQTemplate
    private RabbitTemplate firstMQTemplate;

	public MsgSender(@Qualifier(value = "firstMQTemplate")RabbitTemplate firstMQTemplate) {
		this.firstMQTemplate=firstMQTemplate;
		firstMQTemplate.setConfirmCallback(this);
	}

	public void sendTopicMsg(String topicExchange, String routingKey, Object content) {
		CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
		try {
			firstMQTemplate.convertAndSend(topicExchange, routingKey, content, correlationId);
		}catch(Exception e) {
			log.error("sendTopicMsg() 发生异常:{}", e.getMessage(), e);
			//消息发送不成功，根据业务属性看是否需要重试或记录到数据库消息发送失败表，然后通过定时任务扫描消息失败表补偿发送
		}
		
	}

	/**
	 * 配置了消息发送为confirm模式，mq会把消息是否成功放入队列的结果会通过回调方式通知给消息的发送端
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("收到消息发送回调通知,id:{}, 是否发送成功:{}, 原因:{}", correlationData, ack, cause);
		if (ack) {
			log.info("消息发送成功, id:{}", correlationData);

		} else {
			log.info("消息发送失败:{}, id:{}", cause, correlationData);
			//对于发送不成功，但不允许丢失的消息，自己要在回调方法中写好处理逻辑。
			//比如记录日志，重试几次，重试还是失败记录发送失败的消息到数据库，通过定时任务的方式补偿发送，知道发送成功为止。
			//或是不重试，直接记录到数据库，有定数任务扫描发送消息表定时补偿发送。
		}
	}

}
