package com.armstore.armeniannativestore.repository;

import com.armstore.armeniannativestore.model.company.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
