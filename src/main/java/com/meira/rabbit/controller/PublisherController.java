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

        var p = new Person();
        p.setId("1");
        p.setName("Emanuel");
        p.setIdade(16);

        publisher.send(p);

        ResponseEntity<Object> build = ResponseEntity.noContent().build();
        return build;
    }

}
