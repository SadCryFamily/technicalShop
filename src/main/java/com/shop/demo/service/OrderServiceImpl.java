package com.shop.demo.service;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.enitity.Order;
import com.shop.demo.mapper.OrderMapper;
import com.shop.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            orderRepository.save(order);

            log.info("CREATED Order[ID: {}, NAME: {}, QUANTITY: {}]",
                    order.getOrderId(), order.getOrderName(), order.getOrdersQuantity());
        } else {
            optional.orElseThrow(RuntimeException::new);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}