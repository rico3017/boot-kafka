package com.example.demo.service;

import com.example.demo.entity.Welcome;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @program: boot-kafka
 * @description:
 * @author: 001977
 * @create: 2018-07-11 11:01
 */
@Component
public class KafkaService {

    private final KafkaTemplate kafkaTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public KafkaService(KafkaTemplate kafkaTemplate, SimpMessagingTemplate simpMessagingTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public boolean send(Welcome welcome){
        ListenableFuture future = kafkaTemplate.send("my-topic", welcome);
        Result res = new Result();
        future.addCallback(s -> {
            res.result = true;
        }, f -> {
            res.result = false;
        });
        return res.result;
    }

//    @Scheduled(fixedRate = 1000L)
//    public void s(){
//        ListenableFuture future = kafkaTemplate.send("my-topic", new Welcome("AAA", "Hello!"));
//        future.addCallback(s -> {
//            System.out.println("Success");
//        }, f -> {
//            System.out.println("Failed");
//        });
//    }

    @KafkaListener(topics = {"my-topic"})
    public void receive(ConsumerRecord<String, Welcome> record){
        GlobalSave.getInstance().add(record.topic(), record.value());   // 目前没用到这句话
        System.err.println("Receive Message:" + record.value());
        simpMessagingTemplate.convertAndSendToUser("kafka-user-id", "/queue/kafkaMsg", record.value());
    }
}
