package com.example.demo.controller;

import com.example.demo.entity.Welcome;
import com.example.demo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: boot-kafka
 * @description:
 * @author: 001977
 * @create: 2018-07-11 11:00
 */
@RestController
public class SimpleController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/")
    public ModelAndView stomp(){
        return new ModelAndView("stomp_home");
    }

    @SubscribeMapping("/firstConnection")
    public Welcome thanks(){
        return new Welcome("...", "Thank You!");
    }

    @MessageMapping("/sendMessageTopic")  //指定要接收消息的地址，类似@RequestMapping
    @SendTo("/topic/webSocketTopic")   //会将接收到的消息发送到指定的路由目的地，所有订阅该消息的前端用户都能收到，属于广播
    public Welcome sendToTopic(@RequestBody Welcome welcome){
        System.out.println("Send-Topic-Msg:" + welcome);
        return welcome;
    }

    @MessageMapping("/sendMessageQueue")
    @SendToUser("/queue/webSocketQueue") //单个用户
    public Welcome sendToQueue(@RequestBody Welcome welcome){
        System.out.println("Send-Queue-Msg:" + welcome);
        return welcome;
    }

    /**
     * P2P,后台模拟推送给前台，需打开@Scheduled注释
     */
    //@Scheduled(fixedRate = 1000L)
    public void send(){
        Welcome welcome = new Welcome("110","Hello!");
        simpMessagingTemplate.convertAndSendToUser("110", "/queue/pushInfo", welcome);
        System.err.println(welcome);
    }

    @MessageMapping("/sendKafka")
    public Welcome sendToKafka(@RequestBody Welcome welcome){
        boolean b = kafkaService.send(welcome);
        if (b)
            System.out.println("Send-Kafka-Msg:" + welcome);
        return welcome;
    }

}
