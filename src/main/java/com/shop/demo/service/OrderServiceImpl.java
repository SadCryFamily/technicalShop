package com.shop.demo.service;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.dto.ViewOrderDto;
import com.shop.demo.enitity.Order;
import com.shop.demo.mapper.OrderMapper;
import com.shop.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseEntity<?> createOrder(CreateOrderDto orderDto) {
        Optional<CreateOrderDto> optional = Optional.ofNullable(orderDto);

        if (optional.isPresent()) {

            Order order = orderMapper.toOrder(optional.get());

            if (order.getOrdersQuantity() <= 0) {
                throw new RuntimeException("Quantity must be lower than one.");
            }

            orderRepository.save(order);

            log.info("CREATED Order[ID: {}, NAME: {}, QUANTITY: {}]",
                    order.getOrderId(), order.getOrderName(), order.getOrdersQuantity());
        } else {
            optional.orElseThrow(RuntimeException::new);
        }

        return ResponseEntity.ok().body("Successfuly created!");
    }

    @Override
    public List<ViewOrderDto> findAllOrders() {
        List<Order> orders = orderRepository.findAll();

        log.info("FOUND {} orders in db.", orders.size());

        return orders.stream()
                .map(order -> orderMapper.toViewOrderDto(order))
                .collect(Collectors.toList());
    }
}
