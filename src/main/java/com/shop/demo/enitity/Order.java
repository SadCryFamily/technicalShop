package com.shop.demo.enitity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Comparable<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;

    @Column(name = "name")
    private String orderName;

    @Column(name = "price")
    private Long orderPrice;

    @Column(name = "quantity")
    private Long ordersQuantity;

    @Column(name = "is_payed")
    private boolean isPayed;

    @ManyToMany(mappedBy = "orders")
    private Set<Customer> customers = new HashSet<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.getOrders().add(this);
    }

    @Override
    public int compareTo(Order o) {
        return o.getOrderId().compareTo(this.getOrderId());
    }
}
