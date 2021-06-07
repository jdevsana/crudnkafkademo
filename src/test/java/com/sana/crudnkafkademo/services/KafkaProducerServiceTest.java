package com.sana.crudnkafkademo.services;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, controlledShutdown = false, topics = { "Book-Transaction-History" }, brokerProperties = {
        "listeners=PLAINTEXT://localhost:3333", "port=3333" })
public class KafkaProducerServiceTest {
    private static String TOPIC = "Book-Transaction-History";

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    private KafkaMessageListenerContainer<String, String> container;

    private BlockingQueue<ConsumerRecord<String, String>> records;

    @BeforeEach
    public void setUp() throws Exception {
        System.setProperty(EmbeddedKafkaBroker.BROKER_LIST_PROPERTY, embeddedKafka.getBrokersAsString());
        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(
                consumerProperties);
        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

        records = new LinkedBlockingQueue<>();

        container.setupMessageListener(new MessageListener<String, String>() {
            @Override
            public void onMessage(ConsumerRecord<String, String> record) {
                records.add(record);
            }
        });

        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
    }

    @AfterEach
    public void tearDown() {
        container.stop();
    }

    @Test
    @DisplayName("Kafka Test")
    public void kafkaPostTest() throws InterruptedException {
        String msg = "Test Message";
        kafkaProducerService.sendMessage(TOPIC, msg);
        Assertions.assertEquals(msg, records.poll(10, TimeUnit.SECONDS).value().replaceAll("\"", ""));
    }

}
