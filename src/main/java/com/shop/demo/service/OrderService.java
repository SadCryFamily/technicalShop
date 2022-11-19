package com.shop.demo.service;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.dto.ViewOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    ResponseEntity<?> createOrder(CreateOrderDto orderDto);

    List<ViewOrderDto> findAllOrders();

}
