package com.example.event_service.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 메세지 큐 기능: 고객 등록 알람 기능
@Component
public class CustomerEventListener {

    @RabbitListener(queues = "${queue.customer}")
    public void receiveCustomerMessage(String message) {
        System.out.println("📩 고객 등록 알림 수신: " + message);
    }
}
