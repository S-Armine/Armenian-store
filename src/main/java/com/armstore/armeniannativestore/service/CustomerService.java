package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.exception.NotFoundException;
import com.armstore.armeniannativestore.model.cart.Cart;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.repository.CustomerRepository;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CustomerService {

    private final PasswordEncoder encoder;
    private final CustomerRepository customerRepository;

    public CustomerService(PasswordEncoder encoder, CustomerRepository customerRepository) {
        this.encoder = encoder;
        this.customerRepository = customerRepository;
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public boolean save(Customer customer) {
        if (findByEmail(customer.getEmail()).isPresent()) {
            return false;
        }
        customer.setPassword(encoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        return true;
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer getCustomer(Principal principal) {
        return findByEmail(principal.getName()).orElseThrow(()->new NotFoundException("Customer not found"));
    }
}
