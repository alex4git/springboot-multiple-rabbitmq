package com.rabbitmq.demo.mq;

import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.demo.config.AbstractMQConfig;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ConsumerMessage {

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue-2", autoDelete = "true"), exchange = @Exchange(value = AbstractMQConfig.EXCHANGE_TOPTIC_ROBOT_STATUS, type = ExchangeTypes.TOPIC), key = AbstractMQConfig.ROUTINGKEY_ROBOT_STATUS
			+ 1), containerFactory = "secondContainerFactory")
	public void receive(Message message, Channel channel) throws IOException {
		 message.getMessageProperties().setContentType("application/json");
		System.out.println("收到消息from Message1 : " + new String(message.getBody()));
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

	}

}
