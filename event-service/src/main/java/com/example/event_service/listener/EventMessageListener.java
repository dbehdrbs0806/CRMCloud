package com.example.event_service.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventMessageListener {

    @RabbitListener(queues = "${queue.name}")
    public void handleMessage(String message) {
        System.out.println("📩 Received message: " + message);
    }

}