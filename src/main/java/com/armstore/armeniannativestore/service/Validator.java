package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.model.customer.Customer;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidCompany(Company company) {
        return isValidPassword(company.getPassword())
                && isValidEmail(company.getEmail());
    }

    public static boolean isValidCustomer(Customer customer) {
        LocalDate now = LocalDate.now();
        LocalDate birthday = customer.getBirthday();
        return birthday.plusYears(18).isBefore(now) &&
                isValidPassword(customer.getPassword()) &&
                isValidEmail(customer.getEmail());
    }

    public static boolean isValidPassword(String password) {
        String rightPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,18}$";
        Pattern pattern = Pattern.compile(rightPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        String rightPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(rightPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
