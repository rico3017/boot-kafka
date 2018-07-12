package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @program: boot-kafka
 * @description:
 * @author: 001977
 * @create: 2018-07-11 11:02
 */
//@Component
//@EnableScheduling
public class KafkaSchedule {

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public KafkaSchedule(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10000)
    public void send(){
        ListenableFuture future = kafkaTemplate.send("haha", "Hello! lalala");
        future.addCallback(o -> System.out.println("Send Success"), t -> System.err.println("Send Failed"));
    }

    @KafkaListener(topics = {"haha"})
    public void receive(String content){
        System.err.println("Receive:" + content);
    }
}
