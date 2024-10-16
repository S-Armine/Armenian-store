package com.armstore.armeniannativestore.model.order;

import com.armstore.armeniannativestore.model.compositekey.ItemKey;
import com.armstore.armeniannativestore.model.company.Product;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="order_items")
public class OrderItem {

    @EmbeddedId
    private ItemKey id;

    @ManyToOne
    @MapsId("collectionId")
    @JoinColumn(name="order_id", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="price")
    private Long price;

    public OrderItem() {}

    public OrderItem(ItemKey id, Order order, Product product, int quantity, Long price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public ItemKey getId() {
        return id;
    }

    public void setId(ItemKey id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && Double.compare(price, orderItem.price) == 0 && Objects.equals(id, orderItem.id) && Objects.equals(order, orderItem.order) && Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, price);
    }
}
