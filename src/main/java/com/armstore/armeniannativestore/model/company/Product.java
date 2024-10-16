package com.armstore.armeniannativestore.model.company;

import com.armstore.armeniannativestore.model.cart.CartItem;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.model.order.OrderItem;
import jakarta.persistence.*;
import org.hibernate.sql.ast.tree.expression.Collation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "image_url")
    private String image;
    @Column(name="in_stock", nullable = false)
    private int inStock;
    private double rating;
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    @ManyToMany(mappedBy = "favoriteProducts")
    private List<Customer> likedCustomers;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    public Product() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public List<Customer> getLikedCustomers() {
        return likedCustomers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return rating == product.rating && Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(description, product.description) && Objects.equals(image, product.image)
                && Objects.equals(company, product.company) && Objects.equals(category, product.category)
                && Objects.equals(reviews, product.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, image, inStock, rating, company, category, reviews, orderItems);
    }
}
