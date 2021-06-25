package com.meira.rabbit.consumer;

import com.meira.rabbit.model.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

//    @RabbitListener(queues = {"${queue.order.name}"})
//    public void receive(@Payload Person person) {
//
//        System.out.println(" MSG: "+person);
//
//        Person pessoa2 = new Person();
//        pessoa2.setName("teste");
//        BeanUtils.copyProperties(person, pessoa2 );
//
//        System.out.println(pessoa2.toString());
//    }
}
