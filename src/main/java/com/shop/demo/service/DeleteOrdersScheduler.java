package com.shop.demo.service;

import com.shop.demo.enitity.Customer;
import com.shop.demo.enitity.Order;
import com.shop.demo.repository.CustomerRepository;
import com.shop.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class DeleteOrdersScheduler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @Scheduled(fixedDelayString = "${shop.scheduler-delete-wait}")
    public void removeUnpayedOrders() {

        List<Customer> checkableCustomers = customerRepository.findAll();

        AtomicInteger deletedOrders = new AtomicInteger();

        checkableCustomers.forEach(customer -> {
            if (customer.getOrders() != null) {
                customer.getOrders().forEach(order -> {

                    if (!order.isPayed()) {
                        Set<Order> orders =
                                new ConcurrentSkipListSet<>(customer.getOrders());

                        orders.remove(order);

                        customer.setOrders(orders);
                        customerRepository.save(customer);

                        deletedOrders.getAndIncrement();
                    }

                });
            }
        }

        );

        log.info("DELETED {} unpayed orders.", deletedOrders);
    }


}
