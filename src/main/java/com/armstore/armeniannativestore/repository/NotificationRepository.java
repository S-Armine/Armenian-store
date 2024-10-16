package com.armstore.armeniannativestore.repository;

import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.model.company.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByCompanyAndRead(Company company, boolean read);

    @Modifying
    @Query("UPDATE Notification n SET n.read = :read WHERE n.id = :id")
    void updateNotificationStatus(@Param("id") Long id, @Param("read") boolean read);
}
