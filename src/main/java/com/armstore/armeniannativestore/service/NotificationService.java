package com.armstore.armeniannativestore.service;

import com.armstore.armeniannativestore.exception.NotFoundException;
import com.armstore.armeniannativestore.model.company.Notification;
import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification findByID(long id) {
        return notificationRepository.findById(id).orElseThrow(()->new NotFoundException("Notification not found"));
    }

    public List<Notification> getReadNotifications(Company company) {
        return notificationRepository.findByCompanyAndRead(company, true);
    }

    public List<Notification> getNotReadNotifications(Company company) {
        return notificationRepository.findByCompanyAndRead(company, false);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public void saveAll(List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void markAsRead(Notification notification) {
        notificationRepository.updateNotificationStatus(notification.getId(), true);
    }
}
