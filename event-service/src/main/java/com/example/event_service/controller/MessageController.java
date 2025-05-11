package com.example.event_service.controller;

import com.example.event_service.config.MessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.message}")                                      // 사용할 메세지를 담은 큐 생성
    private String queueName;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping()
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        rabbitTemplate.convertAndSend(queueName, request.getMessage());
        return ResponseEntity.ok("메시지를 큐에 전송했습니다: " + request.getMessage());
    }

}
