package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.service.CompanyService;
import com.armstore.armeniannativestore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/customer/search")
public class SearchController {

    private final CompanyService companyService;
    private final ProductService productService;

    public SearchController(CompanyService companyService, ProductService productService) {
        this.companyService = companyService;
        this.productService = productService;
    }

    @GetMapping
    public String search(@RequestParam(name = "query", required = false) String query, Model model) {
        model.addAttribute("query", query);
        if (query != null && !query.isEmpty()) {
            List<Company> companies = companyService.findByNameContaining(query);
            Set<Product> searchResults = new HashSet<>(productService.search(query));
            if (!companies.isEmpty()) {
                companies.forEach((company) -> searchResults.addAll(productService.findByCompany(company)));
            }
            model.addAttribute("searchResults", searchResults);
        }
        return "search";
    }
}
