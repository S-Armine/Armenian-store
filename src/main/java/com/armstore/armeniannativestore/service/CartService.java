package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.model.cart.Cart;
import com.armstore.armeniannativestore.model.cart.CartItem;
import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.compositekey.ItemKey;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.repository.CartItemRepository;
import com.armstore.armeniannativestore.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CustomerService customerService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CustomerService customerService, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.customerService = customerService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart getCart(Principal principal) {
        Customer customer = customerService.getCustomer(principal);
        Cart cart = getByCustomer(customer);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
        }
        cartRepository.save(cart);
        return cart;
    }

    public Cart getByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer).orElse(null);
    }

    @Transactional
    public boolean addToCart(Cart cart, Product product, int quantity) {
        if (quantity > product.getInStock()) {
            return false;
        }
        List<CartItem> cartItems = cart.getProducts();
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            cart.setProducts(cartItems);
        }
        boolean alreadyInCart = cartItems.stream().anyMatch(item-> item.getProduct().equals(product));
        if (alreadyInCart) {
            cartItems.forEach(cartItem -> {
                if (cartItem.getProduct().equals(product)) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    cartItemRepository.save(cartItem);
                }
            });
        } else {
            CartItem item = new CartItem(cart, product, quantity);
            item.setId(new ItemKey(cart.getId(), product.getId()));
            cartItemRepository.save(item);
        }
        return true;
    }

    @Transactional
    public boolean removeFromCart(Cart cart, Product product, int quantity) {
        List<CartItem> cartItems = cart.getProducts();
        if (!isAvailableForRemoval(cartItems, product, quantity)) {
            return false;
        }
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(product)) {
                int newQuantity = cartItem.getQuantity() - quantity;
                for (int i = 0; i < 10000; i++) {
                    System.out.println(newQuantity);
                }
                if (newQuantity > 0) {
                    cartItem.setQuantity(newQuantity);
                    cartItemRepository.save(cartItem);
                } else {
                    cartItemRepository.deleteByCartIdAndProductId(cart.getId(), product.getId());
                }
                break;
            }
        }
        return true;
    }

    private boolean isAvailableForRemoval(List<CartItem> cartItems, Product product, int quantity) {
        if (cartItems == null || cartItems.isEmpty()) {
            return false;
        }
        boolean inCart = cartItems.stream().anyMatch(item-> item.getProduct().equals(product));
        if (!inCart) {
            return false;
        }
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                if (item.getQuantity() >= quantity) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeAllCartItems(Cart cart) {
        List<CartItem> cartItems = cart.getProducts();
        if (cartItems != null && !cartItems.isEmpty()) {
            cartItems.forEach(cartItem -> {
                cartItemRepository.deleteByCartIdAndProductId(cart.getId(), cartItem.getProduct().getId());
            });
        }
    }
}
