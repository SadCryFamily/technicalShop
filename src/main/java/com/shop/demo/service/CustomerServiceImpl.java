package com.shop.demo.service;

import com.shop.demo.dto.CreateCustomerDto;
import com.shop.demo.enitity.Customer;
import com.shop.demo.mapper.CustomerMapper;
import com.shop.demo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ResponseEntity<?> createCustomer(CreateCustomerDto customerDto) {
        Optional<CreateCustomerDto> optional = Optional.ofNullable(customerDto);

        if (optional.isPresent()) {

            Customer customer = customerMapper.toCustomer(optional.get());
            customerRepository.save(customer);

            log.info("CREATED Customer [ID: {}, NAME: {}, EMAIL: {}",
                    customer.getId(), customer.getCustomerName(), customer.getCustomerEmail());

        } else {
            optional.orElseThrow(RuntimeException::new);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
