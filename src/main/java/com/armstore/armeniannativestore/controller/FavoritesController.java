package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.service.CustomerService;
import com.armstore.armeniannativestore.service.FavoriteProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/favorites")
public class FavoritesController {

    private final CustomerService customerService;
    private final FavoriteProductsService favoriteProductsService;

    public FavoritesController(CustomerService customerService, FavoriteProductsService favoriteProductsService) {
        this.customerService = customerService;
        this.favoriteProductsService = favoriteProductsService;
    }

    @GetMapping
    public String showFavorites(Principal principal, Model model) {
        Customer customer = customerService.getCustomer(principal);
        List<Product> likedProducts = customer.getFavoriteProducts();
        model.addAttribute("hasProducts", likedProducts != null && !likedProducts.isEmpty());
        model.addAttribute("likedProducts", likedProducts);
        return "favorites";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addFavorite(@RequestBody Map<String, Long> productID, Principal principal) {
        Customer customer = customerService.getCustomer(principal);
        boolean isAdded = favoriteProductsService.addFavoriteProduct(customer, productID.get("productID"));
        if (isAdded) {
            return ResponseEntity.ok("Added successfully");
        }
        return ResponseEntity.badRequest().body("Product already in the favorites.");
    }

    @DeleteMapping("/remove/{productID}")
    @ResponseBody
    public ResponseEntity<String> removeFavoriteProduct(@PathVariable("productID") Long productID, Principal principal) {
        Customer customer = customerService.getCustomer(principal);
        boolean isRemoved = favoriteProductsService.removeFavoriteProduct(customer, productID);
        return isRemoved ?
                ResponseEntity.ok("Removed product successfully") :
                ResponseEntity.badRequest().body("Product was not in the favorites.");
    }
}
