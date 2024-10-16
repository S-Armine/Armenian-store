package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.exception.NotFoundException;
import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.repository.CustomerRepository;
import com.armstore.armeniannativestore.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteProductsService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public FavoriteProductsService(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public boolean addFavoriteProduct(Customer customer, Long productID) {
        Optional<Product> productOptional = productRepository.findById(productID);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (!customer.getFavoriteProducts().contains(product)) {
                customer.getFavoriteProducts().add(product);
                customerRepository.save(customer);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean removeFavoriteProduct(Customer customer, Long productID) {
        Product product = productRepository.findById(productID).orElseThrow(()->new NotFoundException("Product not found"));
        boolean isRemoved = customer.getFavoriteProducts().remove(product);
        customerRepository.save(customer);
        return isRemoved;
    }
}
