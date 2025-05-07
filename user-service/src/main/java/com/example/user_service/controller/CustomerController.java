package com.example.user_service.controller;


import com.example.user_service.domain.entity.Customer;
import com.example.user_service.domain.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CustomerController {

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        return ResponseEntity.ok(savedCustomer);

    }

    @GetMapping()
    public ResponseEntity<Customer>

}
