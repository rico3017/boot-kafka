package com.example.demo.entity;

import java.io.Serializable;

/**
 * @program: boot-kafka
 * @description:
 * @author: 001977
 * @create: 2018-07-11 11:36
 */
public class Welcome implements Serializable {

    private String name;
    private String greeting;
    private String topic;

    public Welcome() {
    }

    public Welcome(String name, String greeting) {
        this.name = name;
        this.greeting = greeting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Welcome{" +
                "name='" + name + '\'' +
                ", greeting='" + greeting + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
