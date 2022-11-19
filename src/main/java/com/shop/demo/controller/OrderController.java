package com.shop.demo.controller;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.dto.ViewOrderDto;
import com.shop.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('USER')")
    public List<ViewOrderDto> findAllOrders() {
        return orderService.findAllOrders();
    }

}
