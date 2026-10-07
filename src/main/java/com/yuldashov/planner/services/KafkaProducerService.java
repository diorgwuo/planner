package com.yuldashov.planner.services;

import com.yuldashov.planner.dto.EmailMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String EMAIL_TOPIC = "EMAIL_SENDING_TASKS";

    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, EmailMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(EmailMessage emailMessage) {
        kafkaTemplate.send(EMAIL_TOPIC, emailMessage);
    }
}
