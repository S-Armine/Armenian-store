package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/home")
    public String home(Principal principal, Model model) {
        Customer customer= customerService.getCustomer(principal);
        model.addAttribute("customer", customer);
        return "customer-home";
    }

}
