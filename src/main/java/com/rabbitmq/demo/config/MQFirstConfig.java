package com.rabbitmq.demo.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;


@Configuration
@ConfigurationProperties("spring.rabbitmq.first")
public class MQFirstConfig extends AbstractMQConfig{
	
    @Bean(name = "firstConnectionFactory")
    @Primary
    public ConnectionFactory connectionFactory() {
        return super.connectionFactory();
    }
    
    @Bean(name = "firstMQTemplate")
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  //必须是prototype类型，消息confirmcallback时才正确
    public RabbitTemplate mqTemplate(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
    	RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());// 发送端类型转化pojo,需要序列化
        return template;
    }

    @Bean(name = "firstContainerFactory")
    public SimpleRabbitListenerContainerFactory firstFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                             @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //开启手动确认方式
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());// 接收端类型转化pojo,需要序列化
        configurer.configure(factory, connectionFactory);
        return factory;
    }
    
    @Bean(value = "firstRabbitAdmin")
    public RabbitAdmin firstRabbitAdmin(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
    
    
    @Bean
    public Queue firstQueue() {
    	return new Queue("queue-1", true, false, true);
    }

}
