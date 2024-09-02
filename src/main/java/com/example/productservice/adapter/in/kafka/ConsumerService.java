package com.example.productservice.adapter.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "customer-created", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
