package com.armstore.armeniannativestore.model.customer;

import com.armstore.armeniannativestore.model.cart.Cart;
import com.armstore.armeniannativestore.model.company.*;
import com.armstore.armeniannativestore.model.order.Order;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column( name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="phone_number", nullable = false)
    private String phoneNumber;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="birthday", nullable = false)
    private LocalDate birthday;
    @Column(name="image", nullable = true)
    private String image = "/images/user_default.jpg";
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Cart cart;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CustomerCard> cards;
    @OneToMany(mappedBy = "customer")
    private Set<Review> reviews;
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;
    @ManyToMany
    @JoinTable(
            name="customer_favorites",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<Product> favoriteProducts;


    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<CustomerCard> getCards() {
        return cards;
    }

    public void setCards(Set<CustomerCard> cards) {
        this.cards = cards;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public List<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFavoriteProducts(List<Product> likedProducts) {
        this.favoriteProducts = likedProducts;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(firstName, customer.firstName)
                && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email)
                && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(password, customer.password)
                && Objects.equals(birthday, customer.birthday) && Objects.equals(image, customer.image)
                && Objects.equals(address, customer.address) && Objects.equals(cart, customer.cart)
                && Objects.equals(cards, customer.cards) && Objects.equals(reviews, customer.reviews)
                && Objects.equals(orders, customer.orders) && Objects.equals(favoriteProducts, customer.favoriteProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, password, birthday, image, address, cards, orders, favoriteProducts);
    }
}
