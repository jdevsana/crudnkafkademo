package com.sana.crudnkafkademo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private static final String TOPIC = "Book-Transaction-History";

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
