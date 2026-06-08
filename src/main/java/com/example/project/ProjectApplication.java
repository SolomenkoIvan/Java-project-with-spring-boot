package com.example.project;

import org.springframework.amqp.rabbit.annotation.EnableRabbit; // Додати імпорт
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
@EnableRabbit
public class ProjectApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(ProjectApplication.class, args);
    }
}