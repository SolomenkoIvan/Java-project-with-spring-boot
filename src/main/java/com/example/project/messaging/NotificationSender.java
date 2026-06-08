package com.example.project.messaging;

import com.example.project.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSender {
    private final RabbitTemplate rabbitTemplate;

    public void sendNotification(String message) {
        System.out.println(" [RabbitMQ] ВІДПРАВКА: " + message);
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, message);
    }
}