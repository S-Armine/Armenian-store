package com.armstore.armeniannativestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.armstore.armeniannativestore.model.company.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByDescriptionContainingIgnoreCase(String description);
    List<Product> findByCompany(Company company);

    @Modifying
    @Query("UPDATE Product p SET p.inStock = :inStock WHERE p.id = :id")
    void updateInStockQuantity(@Param("id") Long id, @Param("inStock") int inStock);

}
