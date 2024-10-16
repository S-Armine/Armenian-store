package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.cart.Cart;
import com.armstore.armeniannativestore.model.customer.Address;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.model.order.Order;
import com.armstore.armeniannativestore.service.CustomerService;
import com.armstore.armeniannativestore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customer/orders")
public class OrderController {

    private final CustomerService customerService;
    private final OrderService orderService;

    public OrderController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrders(Model model, Principal principal) {
        Customer customer = customerService.getCustomer(principal);
        List<Order> orders = orderService.findAllByCustomer(customer);
        addModelAttributes(model, orders);
        return "orders";
    }

    @GetMapping("/{orderID}")
    public String showOrderDetail(Principal principal, Model model, @PathVariable("orderID") long orderID) {
        Customer customer = customerService.getCustomer(principal);
        Order order = orderService.findByCustomerAndId(customer, orderID);
        model.addAttribute("order", order);
        model.addAttribute("orderItems", order.getOrderItems());
        return "order-detail";
    }

    @GetMapping("/confirm")
    public String showOrderConfirmationPage(Principal principal, Model model) {
        Customer customer = customerService.getCustomer(principal);
        Cart cart = customer.getCart();
        boolean isAddressNotSet = customer.getAddress() == null;
        if (isAddressNotSet) {
            customer.setAddress(new Address());
        }
        model.addAttribute("customer", customer);
        model.addAttribute("isAddressNotSet", isAddressNotSet);
        boolean hasProductsInCart = cart != null && cart.getProducts() != null && !cart.getProducts().isEmpty();
        if (hasProductsInCart) {
            model.addAttribute("cartItems", cart.getProducts());
        }
        model.addAttribute("hasProductsInCart", hasProductsInCart);
        return "order-confirm";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@ModelAttribute Customer customer, Principal principal) {
        Customer authenticatedCustomer = customerService.getCustomer(principal);
        authenticatedCustomer.setAddress(customer.getAddress());
        customer.getAddress().setCustomer(authenticatedCustomer);
        customerService.updateCustomer(authenticatedCustomer);
        orderService.makeOrderFromCart(authenticatedCustomer);
        return "redirect:/customer/orders";
    }

    private void addModelAttributes(Model model, List<Order> orders) {
        boolean hasOrders = orders != null && !orders.isEmpty();
        model.addAttribute("hasOrders", hasOrders);
        if (hasOrders) {
            List<Order> activeOrders = orderService.findActiveOrders(orders);
            List<Order> endedOrders = orderService.findEndedOrders(orders);
            boolean hasActiveOrders = activeOrders != null && !activeOrders.isEmpty();
            boolean hasEndedOrders = endedOrders != null && !endedOrders.isEmpty();
            model.addAttribute("hasActiveOrders", hasActiveOrders);
            model.addAttribute("hasEndedOrders", hasEndedOrders);
            model.addAttribute("activeOrders", activeOrders);
            model.addAttribute("endedOrders", endedOrders);
        }
    }
}
