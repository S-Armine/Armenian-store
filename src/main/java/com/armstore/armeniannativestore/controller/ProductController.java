package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String products(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("/{productID}")
    public String products(@PathVariable String productID, Model model) {
        long id = Long.parseLong(productID);
        Product product = productService.findById(id);
        model.addAttribute("hasReviews", product.getReviews()!=null && !product.getReviews().isEmpty());
        System.out.println(product.getReviews());
        model.addAttribute("product", product);
        return "product";
    }
}
