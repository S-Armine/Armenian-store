package com.armstore.armeniannativestore.repository;

import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.model.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import com.armstore.armeniannativestore.model.order.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer(Customer customer);

    List<Order> findAllByOrderDate(LocalDate orderDate);

    List<Order> findAllByStatus(Status status);
}
