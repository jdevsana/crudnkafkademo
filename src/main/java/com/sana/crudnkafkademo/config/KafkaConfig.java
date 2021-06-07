package com.sana.crudnkafkademo.config;

import com.sana.crudnkafkademo.model.Book;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * This configuration class essential Kafka server configurations
 */
@Configuration
@AutoConfigureBefore
public class KafkaConfig {

    /**
     * This method sets the configurations for the Kafka server
     * To run the test cases need disable line 34 and enable line 35
     * @return
     */
    @Bean
    public ProducerFactory<String, Book> producerFactory()
    {
        Map<String,Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:3333");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return  new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * This method creates the KafkaTemplate
     * @return KafkaTemplate
     */
    @Bean
    public KafkaTemplate kafkaTemplate()
    {
        return  new KafkaTemplate<>(producerFactory());
    }
}
