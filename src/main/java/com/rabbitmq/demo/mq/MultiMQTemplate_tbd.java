package com.rabbitmq.demo.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
//public abstract class MultiMQTemplate {
//	由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
// 	@Autowired
//    @Qualifier(value = "firstMQTemplate")
//    public AmqpTemplate firstMQTemplate;
//
//    @Autowired
//    @Qualifier(value = "secondMQTemplate")
//    public AmqpTemplate secondMQTemplate;
//
//}
