package com.example.user_service.service;


import com.example.user_service.domain.dto.CustomerDto;
import com.example.user_service.domain.entity.Customer;
import com.example.user_service.domain.repository.CustomerRepository;
import com.example.user_service.domain.repository.PhoneRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PhoneRepository phoneRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    private final RabbitTemplate rabbitTemplate;                    // RabbitMQ 템플릿 객체 선언

    @Value("${queue.customer}")                                     // 사용 생성할 queue 선언
    private String queueName;                                       // queueName = customer-queue

    @Autowired                                                              // CustomerRepository 사용의 생성자 주입
    public CustomerService(CustomerRepository customerRepository, PhoneRepository phoneRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper mapper, RabbitTemplate rabbitTemplate) {
        this.customerRepository = customerRepository;
        this.phoneRepository = phoneRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }


    public CustomerDto createCustomer(CustomerDto customerDto) {
        customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));              // 비밀번호 암호화
        customerDto.setId(UUID.randomUUID().toString());                                         // 랜덤 ID 및 기기번호 생성
        String serial;                                                                           // 랜덤 기기 번호 생성 (중복 방지)
        do {
            serial = UUID.randomUUID().toString();
        } while (phoneRepository.existsByDeviceSerialNumber(serial));
        if (customerDto.getPhone() != null) {
            customerDto.getPhone().setDeviceSerialNumber(serial);
        }
        Customer customer = mapper.map(customerDto, Customer.class);                             // DTO → Entity / mapper 사용

        // ➤ 일부 필드만 추출하여 Map 생성
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("id", customer.getId());
        messageMap.put("name", customer.getName());
        messageMap.put("email", customer.getEmail());

        try {
            // ➤ Jackson ObjectMapper를 사용하여 JSON 문자열로 변환
            ObjectMapper mapper = new ObjectMapper();
            String jsonMessage = mapper.writeValueAsString(messageMap);

            // ➤ RabbitMQ 전송 (SimpleMessageConverter를 사용해 문자열 전송)
            rabbitTemplate.convertAndSend(queueName, jsonMessage);
        } catch (JsonProcessingException e) {
            // 예외 처리 로직 (로깅, fallback 등)
            throw new RuntimeException("RabbitMQ 메시지 전송 중 JSON 처리 오류 발생", e);
        }

        // queue 생성 부분
        customerRepository.save(customer);                                                       // Entity save
        return mapper.map(customer, CustomerDto.class);                                          // Controller에서 return 사용하기 위해
    }

    public void deleteCustomerById(String id) {
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("고객 ID가 존재하지 않습니다: " + id);
        }
        customerRepository.deleteById(id);
    }

    public Iterable<Customer> getCustomerByAll() {
        return customerRepository.findAll();
    }
}
