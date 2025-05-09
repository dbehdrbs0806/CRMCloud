package com.example.user_service.controller;


import com.example.user_service.domain.dto.CustomerDto;
import com.example.user_service.domain.entity.Customer;
import com.example.user_service.service.CustomerService;
import jakarta.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Customer deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("고객 ID가 존재하지 않습니다: " + id);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        Iterable<Customer> customers = customerService.getCustomerByAll();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }
}
