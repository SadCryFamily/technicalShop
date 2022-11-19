package com.shop.demo.service;

import com.shop.demo.dto.CreateOrderDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<?> createOrder(CreateOrderDto orderDto);

}
