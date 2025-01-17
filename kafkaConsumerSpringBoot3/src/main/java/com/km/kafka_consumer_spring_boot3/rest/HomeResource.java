package com.km.kafka_consumer_spring_boot3.rest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
public class HomeResource {

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

//    @GetMapping("/")
//    public String index() {
//        return "\"Hello World!\"";
//    }

//    @GetMapping("/lastorder")
    @GetMapping("/")
    public List<String> getLastOrder() {
        List<String> messages = new ArrayList<>();
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerFactory.getConfigurationProperties())) {
            TopicPartition partition = new TopicPartition("home", 0); // Adjust partition if needed
            consumer.assign(Collections.singletonList(partition));

            // Get the last offset
            long endOffset = consumer.endOffsets(Collections.singletonList(partition)).get(partition);

            // Fetch the last 10 messages
            long startOffset = Math.max(endOffset - 10, 0);
            consumer.seek(partition, startOffset);
            // Poll messages
            for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofSeconds(1))) {
                messages.add(record.value());
            }
        }
        return messages;
    }

}
