package com.shop.demo.service;

import com.shop.demo.auth.entity.ERole;
import com.shop.demo.auth.entity.Role;
import com.shop.demo.config.jwt.JwtUtils;
import com.shop.demo.dto.ViewOrderDto;
import com.shop.demo.enitity.Customer;
import com.shop.demo.enitity.Order;
import com.shop.demo.mapper.CustomerMapper;
import com.shop.demo.mapper.OrderMapper;
import com.shop.demo.repository.CustomerRepository;
import com.shop.demo.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderMapper orderMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private OrderRepository orderRepository;

    private Order testableOrder;

    private Customer testableCustomer;

    private String jwtTokenTemplate;

    @BeforeEach
    public void setUp() throws Exception {

        jwtTokenTemplate =
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmlzdHJhdG9yIiwiaWF0IjoxNjY4OTYzNjI5L" +
                        "CJleHAiOjE2Njg5OTk2Mjl9.1llWaoz-I2u0TmI-XmWWY3MBUrBjx2TyOFZhc4H8pGHhHkjA9Gltc" +
                        "8Zbcjptc9sSWusJ-rOPkLVmM2tP92d4EA";

        testableOrder = Order.builder()
                .orderId(1L)
                .orderName("Mac book Pro 16'")
                .orderPrice(1350L)
                .ordersQuantity(2L)
                .isPayed(false)
                .customers(new HashSet<>())
                .build();

        Set<Role> customerRoles = new HashSet<>();
        customerRoles.add(
                Role.builder()
                        .id(1L)
                        .name(ERole.ROLE_USER)
                        .build()
        );


        testableCustomer = Customer.builder()
                .id(1L)
                .customerName("Bill Hawk")
                .customerPassword("secret")
                .customerEmail("bill.hawk@gmail.com")
                .roles(customerRoles)
                .orders(new HashSet<>())
                .build();
    }

    @Test
    void makeAnOrder() {

        when(customerRepository.existsByCustomerName(anyString()))
                        .thenReturn(true);

        when(customerRepository.findByCustomerName(anyString()))
                .thenReturn(testableCustomer);

        when(orderRepository.getReferenceById(anyLong()))
                .thenReturn(testableOrder);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(testableCustomer);

        ResponseEntity<?> actualResult =
                customerService.makeAnOrder(jwtTokenTemplate, 1L);

        assertEquals(HttpStatus.OK.value(), actualResult.getStatusCodeValue());
    }

    @Test
    void getAnOrder() {

        when(customerRepository.existsByCustomerName(anyString()))
                .thenReturn(true);

        Set<Order> testableCustomerOrders = testableCustomer.getOrders();
        testableCustomerOrders.add(testableOrder);

        testableCustomer.setOrders(testableCustomerOrders);

        when(customerRepository.findByCustomerName(anyString()))
                .thenReturn(testableCustomer);

        ViewOrderDto expectedViewDto = orderMapper.toViewOrderDto(testableOrder);

        ResponseEntity<ViewOrderDto> actualViewDto =
                customerService.getAnOrder(jwtTokenTemplate, 1L);

        assertEquals(HttpStatus.OK.value(), actualViewDto.getStatusCodeValue());
        assertEquals(expectedViewDto.getName(), actualViewDto.getBody().getName());
        assertEquals(expectedViewDto.getPrice(), actualViewDto.getBody().getPrice());

    }

    @Test
    void payAnOrder() {

        when(customerRepository.existsByCustomerName(anyString()))
                .thenReturn(true);

        Set<Order> testableCustomerOrders = testableCustomer.getOrders();
        testableCustomerOrders.add(testableOrder);

        testableCustomer.setOrders(testableCustomerOrders);

        when(customerRepository.findByCustomerName(anyString()))
                .thenReturn(testableCustomer);

        when(orderRepository.getReferenceById(anyLong()))
                .thenReturn(testableOrder);

        testableOrder.setPayed(true);

        ViewOrderDto expectedViewDto =
                orderMapper.toViewOrderDto(testableOrder);

        ResponseEntity<ViewOrderDto> actualViewDto =
                customerService.payAnOrder(jwtTokenTemplate, 1L);

        assertEquals(HttpStatus.OK.value(), actualViewDto.getStatusCodeValue());
        assertEquals(expectedViewDto.getName(), actualViewDto.getBody().getName());
        assertEquals(expectedViewDto.isPayed(), actualViewDto.getBody().isPayed());

    }
}