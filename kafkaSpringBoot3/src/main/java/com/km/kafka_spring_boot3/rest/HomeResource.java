package com.km.kafka_spring_boot3.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeResource {

    private final KafkaTemplate kafkaTemplate;

    private final String HOME_TOPIC = "home";

    public HomeResource(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/")
    public String index() {
        return "\"Hello World!\"";
    }

    @PostMapping
    public void sendMessage(@RequestBody User user) {
        kafkaTemplate.send(HOME_TOPIC, user.toString());
        System.out.println("User Published: " + user);
    }

}

@Getter
@Setter
@ToString
class User {
    private String name;
    private String mobileNumber;
    private String email;
    private String password;
}