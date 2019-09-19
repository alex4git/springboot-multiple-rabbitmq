package com.rabbitmq.demo.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public abstract class MultiMQTemplate {
	
	
	//由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    protected RabbitTemplate firstMQTemplate;
    
    protected RabbitTemplate secondMQTemplate;
    
    /**
     * 	构造方法注入rabbitTemplate
     */
    @Autowired
    public MultiMQTemplate(@Qualifier(value = "firstMQTemplate") AmqpTemplate firstMQTemplate,
    		@Qualifier(value = "secondMQTemplate") AmqpTemplate secondMQTemplate
    		) {
        this.firstMQTemplate = (RabbitTemplate) firstMQTemplate;
        this.secondMQTemplate = (RabbitTemplate) secondMQTemplate;

    }
 


}
