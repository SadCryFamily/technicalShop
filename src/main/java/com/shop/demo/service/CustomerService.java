package com.shop.demo.service;

import com.shop.demo.dto.CreateCustomerDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<?> createCustomer(CreateCustomerDto customerDto);

}
