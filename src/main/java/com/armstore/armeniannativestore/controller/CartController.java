package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.cart.Cart;
import com.armstore.armeniannativestore.service.CartService;
import com.armstore.armeniannativestore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping()
    public String showCart(Principal principal, Model model) {
        Cart cart = cartService.getCart(principal);
        if (cart.getProducts()!= null && !cart.getProducts().isEmpty()) {
            model.addAttribute("hasProducts", true);
            model.addAttribute("cart", cart);
        } else {
            model.addAttribute("hasProducts", false);
        }
        return "cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addCartItem(@RequestBody Map<String, Long> productID, Principal principal) {
        int quantity = 1;
        Cart cart = cartService.getCart(principal);
        Product product = productService.findById(productID.get("productID"));
        if (cartService.addToCart(cart, product, quantity)) {
            return ResponseEntity.ok("Successfully added to the cart");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Not enough in stock to add to the cart");
    }

    @DeleteMapping("/remove/{productID}")
    @ResponseBody
    public ResponseEntity<String> removeCartItem(@PathVariable("productID") Long productID, Principal principal) {
        int quantity = 1;
        Cart cart = cartService.getCart(principal);
        Product product = productService.findById(productID);
        boolean isRemoved = cartService.removeFromCart(cart, product, quantity);
        return isRemoved ?
                ResponseEntity.ok("Removed product successfully") :
                ResponseEntity.badRequest().body("Product was not in the cart.");
    }
}
