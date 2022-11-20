package com.shop.demo.service;

import com.shop.demo.dto.ViewOrderDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<ViewOrderDto> getAnOrder(String jwtToken, Long orderId);

    ResponseEntity<ViewOrderDto> payAnOrder(String jwtToken, Long orderId);

    ResponseEntity<?> makeAnOrder(String jwtToken, Long orderId);

}
