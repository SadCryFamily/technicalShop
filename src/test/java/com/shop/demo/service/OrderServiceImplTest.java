package com.shop.demo.service;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.dto.ViewOrderDto;
import com.shop.demo.enitity.Order;
import com.shop.demo.mapper.OrderMapper;
import com.shop.demo.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @MockBean
    private OrderRepository orderRepository;

    private Order testableOrder;

    @BeforeEach
    public void setUp() throws Exception {
        testableOrder = Order.builder()
                .orderId(1L)
                .orderName("Iphone 13 Pro Max")
                .orderPrice(1300L)
                .ordersQuantity(1L)
                .isPayed(false)
                .customers(new HashSet<>())
                .build();
    }

    @Test
    void createOrder() {

        when(orderRepository.save(testableOrder))
                .thenReturn(testableOrder);

        CreateOrderDto createOrderDto =
                orderMapper.toOrderDto(testableOrder);

        ResponseEntity<?> actualCode =
                orderService.createOrder(createOrderDto);

        assertEquals(HttpStatus.OK.value(), actualCode.getStatusCodeValue());
    }

    @Test
    void findAllOrders() {

        List<Order> ordersList = List.of(testableOrder);

        when(orderRepository.findAll()).thenReturn(ordersList);

        List<ViewOrderDto> expectedOrdersList = ordersList.stream()
                .map(order -> orderMapper.toViewOrderDto(order))
                .collect(Collectors.toList());

        List<ViewOrderDto> actualOrdersList = orderService.findAllOrders();

        assertEquals(expectedOrdersList.get(0).getName(), actualOrdersList.get(0).getName());
        assertEquals(expectedOrdersList.get(0).getPrice(), actualOrdersList.get(0).getPrice());

    }
}