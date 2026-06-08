package com.example.project.messaging;

import com.example.project.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive(String message) {
        System.out.println(" [RabbitMQ] ОТРИМАНО ПОВІДОМЛЕННЯ: " + message);
        System.out.println(" [RabbitMQ] Надсилаємо email-сповіщення про нову книгу...");
    }
}