package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.company.Review;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public ReviewService(ReviewRepository reviewRepository, CustomerService customerService, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public void save(Review review, Principal principal, Long productID) {
        Customer customer = customerService.getCustomer(principal);
        Product product = productService.findById(productID);
        review.setCustomer(customer);
        review.setProduct(product);
        productService.updateRating(product, review);
    }
}
