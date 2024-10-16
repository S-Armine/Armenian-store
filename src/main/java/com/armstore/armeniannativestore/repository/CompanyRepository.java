package com.armstore.armeniannativestore.repository;

import com.armstore.armeniannativestore.model.company.Company;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByEmail(String email);
    Optional<Company> findByName(String name);
    List<Company> findByNameContainingIgnoreCase(String name);
}
