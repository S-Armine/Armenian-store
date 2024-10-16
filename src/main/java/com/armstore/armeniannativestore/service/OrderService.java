package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.exception.NotFoundException;
import com.armstore.armeniannativestore.model.cart.Cart;
import com.armstore.armeniannativestore.model.cart.CartItem;
import com.armstore.armeniannativestore.model.company.Notification;
import com.armstore.armeniannativestore.model.company.Product;
import com.armstore.armeniannativestore.model.compositekey.ItemKey;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.model.order.Order;
import com.armstore.armeniannativestore.model.order.OrderItem;
import com.armstore.armeniannativestore.model.order.Status;
import com.armstore.armeniannativestore.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    private final CartService cartService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, NotificationService notificationService, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
        this.cartService = cartService;
        this.productService = productService;
    }

    public List<Order> findAllByCustomer(Customer customer) {
        return orderRepository.findAllByCustomer(customer);
    }

    public List<Product> findAllProductsInActiveOrder() {
        List<Product> products = new ArrayList<>();
        List<Order> orders = getAllActiveOrders();
        for (Order order : orders) {
            order.getOrderItems().forEach(orderItem -> products.add(orderItem.getProduct()));
        }
        return products;
    }

    public List<Order> getAllActiveOrders() {
        return orderRepository.findAllByStatus(Status.ACTIVE);
    }

    public List<Order> findActiveOrders(List<Order> orders) {
        return orders.stream().filter(order->order.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList());
    }

    public List<Order> findEndedOrders(List<Order> orders) {
        return orders.stream().filter(order->order.getStatus().equals(Status.ENDED)).collect(Collectors.toList());
    }

    public Order findById(long id) {
        return orderRepository.findById(id).orElseThrow(()->new NotFoundException("Order not found"));
    }

    public Order findByCustomerAndId(Customer customer, long id) {
        Order order = orderRepository.findById(id).orElseThrow(()->new NotFoundException("Order not found"));
        if (order.getCustomer().equals(customer)) {
            return order;
        }
        throw new NotFoundException("Order does not belong to customer");
    }

    @Transactional
    public void makeOrderFromCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            throw new NotFoundException("Products not found to order.");
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setStatus(Status.ACTIVE);
        Set<OrderItem> orderItems = getItemsFromCartToOrder(cart, order);
        order.setOrderItems(orderItems);
        order.setTotalAmount(orderItems.stream().map(OrderItem::getPrice).reduce((long) 0, Long::sum));
        notificationService.saveAll(notifyCompaniesOfOrder(order));
        orderRepository.save(order);
    }

    public Set<OrderItem> getItemsFromCartToOrder(Cart cart, Order order) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem: cart.getProducts()) {
            OrderItem orderItem = new OrderItem();
            Product product = cartItem.getProduct();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice((long) cartItem.getQuantity() * cartItem.getProduct().getPrice());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            ItemKey itemKey = new ItemKey(order.getId(), cartItem.getProduct().getId());
            orderItem.setId(itemKey);
            orderItems.add(orderItem);
            productService.updateInStockQuantity(product, product.getInStock()-orderItem.getQuantity());
        }
        cartService.removeAllCartItems(cart);
        return orderItems;
    }

    public List<Notification> notifyCompaniesOfOrder(Order order) {
        List<Notification> notifications = new ArrayList<>();
        for (OrderItem orderItem: order.getOrderItems()) {
            Notification notification = new Notification();
            notification.setCompany(orderItem.getProduct().getCompany());
            String orderMessage = "New order was done for " +
                    orderItem.getProduct().getName() +
                    " in order " +
                    order.getId();
            notification.setMessage(orderMessage);
            notifications.add(notification);
        }
        return notifications;
    }

    @Scheduled(fixedRate = 86400000)
    public void updateOrderStatus() {
        LocalDate lastWeek = LocalDate.now().minusDays(7);
        List<Order> orders = orderRepository.findAllByOrderDate(lastWeek);
        if (orders == null || orders.isEmpty()) {
            return;
        }
        orders.forEach(order -> order.setStatus(Status.ENDED));
        orderRepository.saveAll(orders);
    }
}
