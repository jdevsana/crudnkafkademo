package com.sana.crudnkafkademo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * This configuration class essential Kafka Topic building configurations
 */
@Configuration
public class KafkaTopicConfiguration {


    /**
     * This method creates a new kafka topic if not exist
     * @return NewTopic if created
     */
    @Bean
    public NewTopic compactTopicExample() {
        return TopicBuilder.name("Book-Transaction-History")
                .partitions(1)
                .replicas(1)
                .build();
    }
}