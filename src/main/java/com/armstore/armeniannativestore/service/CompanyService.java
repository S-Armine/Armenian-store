package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.exception.NotFoundException;
import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.order.OrderItem;
import com.armstore.armeniannativestore.model.order.Status;
import com.armstore.armeniannativestore.repository.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyService(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean save(Company company) {
        if (companyRepository.findByEmail(company.getEmail()).isPresent() ||
                companyRepository.findByName(company.getName()).isPresent()) {
            return false;
        }
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        companyRepository.save(company);
        return true;
    }

    public List<Company> findByNameContaining(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(long id) {
        return companyRepository.findById(id).orElseThrow(()-> new NotFoundException("Company not found"));
    }

    public Company findByEmail(String name) {
        return companyRepository.findByEmail(name).orElseThrow(() -> new NotFoundException("Company not found"));
    }

    public Company getCompany(Principal principal) {
        return findByEmail(principal.getName());
    }

    public List<OrderItem> getAllActiveOrderItems(Company company) {
        List<OrderItem> items = new ArrayList<>();
        for (Product product : company.getProducts()) {
            product.getOrderItems().forEach(item -> {
                if (item.getOrder().getStatus().equals(Status.ACTIVE)) {
                    items.add(item);
                }
            });
        }
        return items;
    }
}