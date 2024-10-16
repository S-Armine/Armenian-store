package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.model.order.OrderItem;
import com.armstore.armeniannativestore.service.CompanyService;
import com.armstore.armeniannativestore.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.security.Principal;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/home")
    public String showHome(Principal principal, Model model) {
        Company company = companyService.getCompany(principal);
        model.addAttribute("company", company);
        model.addAttribute("hasProducts", company.getProducts() != null && !company.getProducts().isEmpty());
        return "company-home";
    }

    @GetMapping("/orders")
    public String showOrders(Principal principal, Model model) {
        Company company = companyService.getCompany(principal);
        List<OrderItem> activeOrderItems = companyService.getAllActiveOrderItems(company);
        boolean hasActiveOrderItems = activeOrderItems != null && !activeOrderItems.isEmpty();
        model.addAttribute("hasActiveOrderItems", hasActiveOrderItems);
        if (hasActiveOrderItems) {
            model.addAttribute("orderItems", activeOrderItems);
        }
        return "company-orders";
    }
}

