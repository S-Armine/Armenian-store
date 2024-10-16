package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.company.Review;
import com.armstore.armeniannativestore.service.ProductService;
import com.armstore.armeniannativestore.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
@RequestMapping("/customer/reviews")
public class ReviewController {

    private final ProductService productService;
    private final ReviewService reviewService;

    public ReviewController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("/add")
    public String showReviewAdd(@RequestParam("productID") Long productID, Model model) {
        Product product = productService.findById(productID);
        model.addAttribute("product", product);
        model.addAttribute("review", new Review());
        return "add-review";
    }

    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review,
                            @RequestParam Long productID,
                            Principal principal,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productService.findById(productID));
            model.addAttribute("review", review);
            return "add-review";
        }
        reviewService.save(review, principal, productID);
        return "redirect:/products/" + productID;
    }
}
