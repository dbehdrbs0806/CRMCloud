package com.example.event_service.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitConfig {

    @Value("${queue.customer}")
    private String customerQueue;

    @Value("${queue.message}")
    private String messageQueue;
    @Bean
    public Queue customerQueue() {
        return new Queue(customerQueue, true); // durable = true (디스크에 저장)
    }

    @Bean
    public Queue messageQueue() {
        return new Queue(messageQueue, true);
    }
}

