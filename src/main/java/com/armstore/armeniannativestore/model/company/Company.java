package com.armstore.armeniannativestore.model.company;

import com.armstore.armeniannativestore.model.customer.Address;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column( name = "name", nullable = false)
    private String name;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="phone_number", nullable = false)
    private String phoneNumber;
    @Column(name="logo_url")
    private String logo;
    @Column(name="password", nullable = false)
    private String password;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CompanyAccount companyAccount;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    public Company() {}

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CompanyAccount getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(CompanyAccount companyAccount) {
        this.companyAccount = companyAccount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(email, company.email) && Objects.equals(phoneNumber, company.phoneNumber) && Objects.equals(logo, company.logo) && Objects.equals(password, company.password) && Objects.equals(products, company.products) && Objects.equals(companyAccount, company.companyAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phoneNumber, logo, password, products, companyAccount);
    }
}
