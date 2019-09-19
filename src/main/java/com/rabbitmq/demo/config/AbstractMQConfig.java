package com.rabbitmq.demo.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import lombok.Data;

@Data
public class AbstractMQConfig {
	
    public static final String EXCHANGE_TOPTIC_ROBOT_STATUS = "topic.robot.status";

    public static final String ROUTINGKEY_ROBOT_STATUS = "*.*.T";
    
    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String virtualHost;

    protected ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

}
