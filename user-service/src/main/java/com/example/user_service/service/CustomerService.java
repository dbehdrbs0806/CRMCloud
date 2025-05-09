package com.example.user_service.service;


import com.example.user_service.domain.dto.CustomerDto;
import com.example.user_service.domain.entity.Customer;
import com.example.user_service.domain.entity.Phone;
import com.example.user_service.domain.repository.CustomerRepository;
import com.example.user_service.domain.repository.PhoneRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PhoneRepository phoneRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    @Autowired                                                              // CustomerRepository 사용의 생성자 주입
    public CustomerService(CustomerRepository customerRepository, PhoneRepository phoneRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.phoneRepository = phoneRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
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
