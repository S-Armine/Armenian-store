package com.armstore.armeniannativestore.model.customer;

import com.armstore.armeniannativestore.model.order.Order;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="customer_cards")
public class CustomerCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="holder_name", nullable = false)
    private String holderName;
    @Column(name="last_digits", nullable = false)
    private int lastDigits;
    @Column(name="expiry_date", nullable = false)
    private LocalDate expiryDate;
    @Column(name="token")
    private String token;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    public CustomerCard() {
    }

    public CustomerCard(Long id, String holderName, int lastDigits, LocalDate expiryDate, String token, Customer customer) {
        this.id = id;
        this.holderName = holderName;
        this.lastDigits = lastDigits;
        this.expiryDate = expiryDate;
        this.token = token;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public int getLastDigits() {
        return lastDigits;
    }

    public void setLastDigits(int lastDigits) {
        this.lastDigits = lastDigits;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCard that = (CustomerCard) o;
        return lastDigits == that.lastDigits && Objects.equals(id, that.id) && Objects.equals(holderName, that.holderName) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(token, that.token) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holderName, lastDigits, expiryDate, token, customer);
    }
}
