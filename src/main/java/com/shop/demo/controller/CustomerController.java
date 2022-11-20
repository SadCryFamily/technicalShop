package com.shop.demo.controller;

import com.shop.demo.config.jwt.JwtUtils;
import com.shop.demo.dto.ViewOrderDto;
import com.shop.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/order/{id}")
    public ResponseEntity<ViewOrderDto> getAnOrder(
            @RequestHeader("Authorization") String jwtToken, @PathVariable ("id") Long orderId) {
        return customerService.getAnOrder(jwtToken, orderId);
    }

    @PostMapping("/order/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<?> makeAnOrder(
            @RequestHeader("Authorization") String jwtToken, @PathVariable ("id") Long orderId) {
        return customerService.makeAnOrder(jwtToken, orderId);
    }

    @PostMapping("/order/{id}/pay")
    public ResponseEntity<ViewOrderDto> payAnOrder(
            @RequestHeader("Authorization") String jwtToken, @PathVariable ("id") Long orderId) {
        return customerService.payAnOrder(jwtToken, orderId);
    }

    @GetMapping("/token")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('USER')")
    public String showToken(@RequestHeader("Authorization") String jwtToken) {
        String parsedToken = jwtToken.substring(7);
        return jwtUtils.getUserNameFromJwtToken(parsedToken);
    }

}
