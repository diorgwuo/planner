package com.yuldashov.planner.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");


        KafkaAdmin kafkaAdmin = new KafkaAdmin(config);
        return kafkaAdmin;
    }

    @Bean
    public NewTopic emailSendingTaskTopic() {
        return TopicBuilder.name("EMAIL_SENDING_TASKS")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
