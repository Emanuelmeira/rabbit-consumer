package com.meira.rabbit.publisher;

import com.meira.rabbit.model.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class DefaultPublisher {

    @Resource(name = "rabbitDefault")
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.order.name}")
    private String defaultQueueName;

    public void send(Person person){
        rabbitTemplate.convertAndSend(defaultQueueName, person);
    }


}
