package com.meira.rabbit.consumer;

import com.meira.rabbit.model.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DefaultConsumer {

    @RabbitListener(queues = {"${queue.order.name}"})
    public void receive(@Payload Person person) {
        System.out.println(" MSG: "+person);
    }


}
