package com.shop.demo.service;

import com.shop.demo.dto.ViewOrderDto;
import com.shop.demo.enitity.Customer;
import com.shop.demo.enitity.Order;
import com.shop.demo.mapper.CustomerMapper;
import com.shop.demo.mapper.OrderMapper;
import com.shop.demo.repository.CustomerRepository;
import com.shop.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<ViewOrderDto> getAnOrder(String jwtToken, Long orderId){
        String tokenUrl = "http://localhost:8080/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtToken);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String expectedUsername = restTemplate.exchange(
                tokenUrl, HttpMethod.GET, entity, String.class).getBody();

        ViewOrderDto expectedViewDto = null;

        if (customerRepository.existsByCustomerName(expectedUsername)) {
            Customer customer =
                    customerRepository.findByCustomerName(expectedUsername);

            Optional<Order> convertedOrder =
                    customer.getOrders().stream()
                            .filter(order -> (Objects.equals(order.getOrderId(), orderId)))
                            .findFirst();

            if (convertedOrder.isPresent()) {
                expectedViewDto = orderMapper.toViewOrderDto(convertedOrder.get());
            }

            log.info("GET Order[NAME: {}, PRICE: {}] for Customer[NAME: {}]",
                    expectedViewDto.getName(),
                    expectedViewDto.getPrice(),
                    customer.getCustomerName());

            return ResponseEntity.ok(expectedViewDto);
        }

        return ResponseEntity.badRequest().body(expectedViewDto);
    }

    @Override
    public ResponseEntity<?> makeAnOrder(String jwtToken, Long orderId) {

        String tokenUrl = "http://localhost:8080/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtToken);

        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        String expectedUsername = restTemplate.exchange(
                tokenUrl, HttpMethod.GET, entity, String.class).getBody();

        if (customerRepository.existsByCustomerName(expectedUsername)) {
            Customer customer =
                    customerRepository.findByCustomerName(expectedUsername);

            Order order =
                    orderRepository.getReferenceById(orderId);

            if (customer.getOrders().contains(order)) {
                throw new RuntimeException("Current order already existed!");
            }

            customer.addOrder(order);

            customerRepository.save(customer);

            log.info("MAKE ORDER [NAME: {}] by Customer[NAME: {}]",
                    order.getOrderName(), customer.getCustomerName());

        } else {
            throw new RuntimeException("Cant find customer/order!");
        }

        return ResponseEntity.ok().body("Successfully make an order!");
    }

    @Override
    public ResponseEntity<ViewOrderDto> payAnOrder(String jwtToken, Long orderId) {
        String tokenUrl = "http://localhost:8080/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtToken);

        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        String expectedUsername = restTemplate.exchange(
                tokenUrl, HttpMethod.GET, entity, String.class).getBody();

        ViewOrderDto viewOrderDto = null;

        if (customerRepository.existsByCustomerName(expectedUsername)) {

            Customer customer =
                    customerRepository.findByCustomerName(expectedUsername);

            Order order =
                    orderRepository.getReferenceById(orderId);

            if (customer.getOrders().contains(order)) {
                Optional<Order> convertedOrder =
                        customer.getOrders().stream()
                                .filter(o -> (Objects.equals(o.getOrderId(), orderId)))
                                .findFirst();

                if (convertedOrder.isPresent()) {
                    convertedOrder.get().setPayed(Boolean.parseBoolean("true"));
                    orderRepository.save(convertedOrder.get());

                    viewOrderDto = orderMapper.toViewOrderDto(convertedOrder.get());
                }

                log.info("PAY for Order[NAME: {}, PRICE: {}] by Customer[NAME: {}]",
                        viewOrderDto.getName(),
                        viewOrderDto.getPrice(),
                        customer.getCustomerName());

                return ResponseEntity.ok().body(viewOrderDto);
            }

        } else {
            throw new RuntimeException("Cant find customer by given ID!");
        }

        return ResponseEntity.badRequest().body(viewOrderDto);
    }
}
