package com.processor.orderprocessing.config;

import com.processor.orderprocessing.messaging.event.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderReceivedTopic() {
        return TopicBuilder.name(KafkaTopics.ORDER_RECEIVED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderProcessedTopic() {
        return TopicBuilder.name(KafkaTopics.ORDER_PROCESSED)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
