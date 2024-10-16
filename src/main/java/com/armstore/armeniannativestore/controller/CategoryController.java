package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.service.CategoryService;
import com.armstore.armeniannativestore.model.company.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/{name}")
    public String showCategory(@PathVariable String name, Model model) {
        Category category = categoryService.findByName(name);
        model.addAttribute("category", category);
        model.addAttribute("categoryProducts", category.getProducts());
        return "category";
    }
}
