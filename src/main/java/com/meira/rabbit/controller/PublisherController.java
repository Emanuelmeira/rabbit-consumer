package com.meira.rabbit.controller;

import com.meira.rabbit.model.Person;
import com.meira.rabbit.publisher.DefaultPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class PublisherController {

    @Autowired
    private DefaultPublisher publisher;

    @PostMapping(value = "/")
    public ResponseEntity<Object> postInRabbit() {

        var person = new Person();
        person.setId("1");
        person.setName("Emanuel");
        person.setAge(16);

        publisher.send(person);

        ResponseEntity<Object> response = ResponseEntity.noContent().build();
        return response;
    }

}
