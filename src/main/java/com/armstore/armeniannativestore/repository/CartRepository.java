package com.armstore.armeniannativestore.repository;

import com.armstore.armeniannativestore.model.cart.Cart;
import com.armstore.armeniannativestore.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomer(Customer customer);

}
