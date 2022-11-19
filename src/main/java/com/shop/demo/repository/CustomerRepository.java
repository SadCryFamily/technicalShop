package com.shop.demo.repository;

import com.shop.demo.enitity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCustomerName(String username);
    Boolean existsByCustomerName(String username);
    Boolean existsByCustomerEmail(String email);

}
