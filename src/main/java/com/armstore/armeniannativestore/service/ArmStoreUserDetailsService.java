package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.repository.CompanyRepository;
import com.armstore.armeniannativestore.repository.CustomerRepository;
import com.armstore.armeniannativestore.model.customer.Customer;
import com.armstore.armeniannativestore.model.company.Company;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ArmStoreUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    public ArmStoreUserDetailsService(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return new User(
                    customer.getEmail(),
                    customer.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
                    );
        }
        Company company = companyRepository.findByEmail(email).orElse(null);
        if (company != null) {
            return new User(
                    company.getEmail(),
                    company.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_COMPANY"))
            );
        }

        throw new UsernameNotFoundException("User was not found");
    }
}
