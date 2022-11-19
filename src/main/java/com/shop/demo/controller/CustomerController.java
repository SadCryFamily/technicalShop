package com.shop.demo.controller;

import com.shop.demo.dto.CreateCustomerDto;
import com.shop.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CreateCustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }
}
