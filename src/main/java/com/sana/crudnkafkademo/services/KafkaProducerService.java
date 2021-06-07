package com.sana.crudnkafkademo.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface KafkaProducerService {

    void sendMessage(String topic, String message);
}
