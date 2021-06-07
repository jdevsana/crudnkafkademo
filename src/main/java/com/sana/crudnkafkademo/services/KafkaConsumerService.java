package com.sana.crudnkafkademo.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = {"Book-Transaction-History"}, groupId = "test-message-group")
    public void consumeContactSupportResponse(String incomingMessage) {
        System.out.println("Message received from event handler '" + incomingMessage + "'");
    }
}
