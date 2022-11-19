package com.shop.demo.controller;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

}
