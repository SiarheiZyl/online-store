package com.online_market.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Class to configure JMS
 *
 * @author Siarhei
 * @version 1.0
 */
@Configuration
@EnableJms
@ComponentScan
@ComponentScan(basePackages = {"com.online_market"})
public class JMSConfig {

    /**
     * URL where JMS server is available
     */
    private static final String BROKER_URL = "tcp://localhost:61616";

    /**
     * Default username
     */
    private static final String BROKER_USERNAME = "admin";

    /**
     * Default password
     */
    private static final String BROKER_PASSWORD = "admin";

    /**
     * Method registers ActiveMQConnectionFactory in spring context.
     * By this method app may to send messages to ActiveMQ server.
     *
     * @return ActiveMQConnectionFactory
     */
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setPassword(BROKER_PASSWORD);
        connectionFactory.setUserName(BROKER_USERNAME);
        return connectionFactory;
    }

    /**
     * Method registers jmsTemplate in spring context.
     * It uses connectionFactory we defined above and give us simple API
     * for sending messages.
     *
     * @return JmsTemplate
     */
    @Bean
    public JmsTemplate jmsTemplate() {

        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    /**
     * Method registers jmsListenerContainerFactory in spring context.
     *
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("1-1");
        return factory;
    }
}
