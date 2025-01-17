package com.km.kafka_consumer_spring_boot3.rest;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class HomeConsumerListener {

    private final String HOME_TOPIC = "home";

    @KafkaListener(topics = HOME_TOPIC, groupId = "my-group")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
