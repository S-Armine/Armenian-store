package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.exception.NotFoundException;
import com.armstore.armeniannativestore.repository.CategoryRepository;

import com.armstore.armeniannativestore.model.company.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(()->new NotFoundException("Category not found"));
    }
}
