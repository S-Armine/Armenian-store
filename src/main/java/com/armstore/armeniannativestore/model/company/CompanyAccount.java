package com.armstore.armeniannativestore.model.company;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="company_accounts")
public class CompanyAccount {

    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Company company;
    @Column(name="last_digits")
    private Integer lastDigits;
    @Column(name="token")
    private String token;
    @Column(name="bank_name")
    private String bankName;

    public CompanyAccount() {}

    public CompanyAccount(Long id, Company company, Integer lastDigits, String token, String bankName) {
        this.id = id;
        this.company = company;
        this.lastDigits = lastDigits;
        this.token = token;
        this.bankName = bankName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getLastDigits() {
        return lastDigits;
    }

    public void setLastDigits(Integer lastDigits) {
        this.lastDigits = lastDigits;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyAccount that = (CompanyAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(company, that.company) && Objects.equals(lastDigits, that.lastDigits) && Objects.equals(token, that.token) && Objects.equals(bankName, that.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, lastDigits, token, bankName);
    }
}
