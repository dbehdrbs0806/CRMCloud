package com.example.event_service.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 메세지 큐 기능: 메세지 수신 기능
@Component
public class MessageQueueListener {

    @RabbitListener(queues = "${queue.message}")
    public void receiveMessage(String message) {
        System.out.println("📨 메시지 수신: " + message);
    }

}
