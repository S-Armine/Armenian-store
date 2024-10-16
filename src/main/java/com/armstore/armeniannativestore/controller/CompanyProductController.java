package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Category;
import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.service.CategoryService;
import com.armstore.armeniannativestore.service.CompanyService;
import com.armstore.armeniannativestore.service.OrderService;
import com.armstore.armeniannativestore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/company/products")
public class CompanyProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;

    public CompanyProductController(ProductService productService, CategoryService categoryService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getProducts() {
        return "company-home";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "product-create";
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Product> addProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("inStock") int inStock,
            @RequestParam("price") int price,
            @RequestParam("category") String categoryName,
            @RequestParam("image") MultipartFile image,
            Principal principal) {
        Category category = categoryService.findByName(categoryName);
        Product product = productService.createProduct(principal, category, name, description, price, inStock, image);
        if (product == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productID}/update")
    public String updateProductPage(@PathVariable Long productID, Model model, Principal principal) {
        Product product = productService.getProduct(principal, productID);
        model.addAttribute("product", product);
        return "product-update";
    }

    @PutMapping("/{productID}")
    @ResponseBody
    public ResponseEntity<String> updateProduct(
            @PathVariable Long productID,
            @RequestBody Product updatedProduct,
            Principal principal) {
        productService.updateProduct(principal, productID, updatedProduct);
        return ResponseEntity.ok().body("Product updated successfully");
    }

    @DeleteMapping("/{productID}/delete")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productID, Principal principal) {
        List<Product> activeProducts = orderService.findAllProductsInActiveOrder();
        Product product = productService.getProduct(principal, productID);
        if (productService.isInActiveOrder(activeProducts, product)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product is in active order. Consider marking it as not available in stock.");
        }
        productService.deleteProductFromCompany(principal, productID);
        return ResponseEntity.ok().body("Product deleted successfully");
    }
}
