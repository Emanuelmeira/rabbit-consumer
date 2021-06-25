package com.meira.rabbit.configuration;



import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host:/}")
    private String virtualHostCustomer;

    @Primary
    @Bean(name = "rabbitDefault")
    public RabbitTemplate rabbitTemplate() {
        return buildRabbitTemplate("default");
    }

    // ---------------------------  configuration publisher ---------------------------

    private RabbitTemplate buildRabbitTemplate(String virtualHost) {
        final var rabbitTemplate = new RabbitTemplate(getConnectionFactory(virtualHost));

        // Para setar trace-id nas mensagens
        // rabbitTemplate.setBeforePublishPostProcessors(this.getMessagePostProcessorBeforePublish());
        // rabbitTemplate.setAfterReceivePostProcessors(this.getMessagePostProcessorAfterReceive());

        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    private CachingConnectionFactory getConnectionFactory(String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        if (virtualHost.contains("default")) {
            connectionFactory.setHost(host);
            if (Objects.nonNull(port)) {
                connectionFactory.setPort(port);
            }

            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);

            if (!"/".equals(virtualHostCustomer)) {
                connectionFactory.setVirtualHost(virtualHostCustomer);
            }
        }

        return connectionFactory;
    }

    @Primary
    @Bean(name = "defaultRabbitAdmin")
    public RabbitAdmin customerRabbitAdmin(@Value("${queue.order.name}") String defaultQueue,
                                           @Value("${spring.rabbitmq.routing.key.default.name}") String exchangeName,
                                           @Value("${spring.rabbitmq.exchange.default.name}") String exchangeRoutingKey){

        RabbitAdmin rabbitAdmin = new RabbitAdmin(getConnectionFactory("default"));
        rabbitAdmin.setAutoStartup(true);

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-queue-type", "classic");

        Queue nameQueue = new Queue(defaultQueue, true, false, false, arguments);
        rabbitAdmin.declareQueue(nameQueue);

        // criando exchange do tipo 'Topic'
        var topicExchange = new TopicExchange(exchangeName, true, false);
        rabbitAdmin.declareExchange(topicExchange);

        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(nameQueue)
                        .to(topicExchange)
                        .with(exchangeName)
        );

        return rabbitAdmin;
    }

    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // --------------------------- configuration consumer ---------------------------

}
