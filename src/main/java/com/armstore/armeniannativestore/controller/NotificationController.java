package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.model.company.Company;
import com.armstore.armeniannativestore.model.company.Notification;
import com.armstore.armeniannativestore.service.CompanyService;
import com.armstore.armeniannativestore.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/company/notifications")
public class NotificationController {

    private final CompanyService companyService;
    private final NotificationService notificationService;

    public NotificationController(CompanyService companyService, NotificationService notificationService) {
        this.companyService = companyService;
        this.notificationService = notificationService;
    }

    @GetMapping()
    public String showNotifications(Principal principal, Model model) {
        Company company = companyService.getCompany(principal);
        List<Notification> notifications = company.getNotifications().stream().filter(not->!not.isRead()).toList();
        boolean hasNotifications = !notifications.isEmpty();
        model.addAttribute("hasNotifications", hasNotifications);
        if (hasNotifications) {
            model.addAttribute("notifications", notifications);
        }
        return "company-notifications";
    }

    @PutMapping("/mark/{notificationID}")
    @ResponseBody
    public ResponseEntity<String> markNotificationAsRead(Principal principal, @PathVariable Long notificationID) {
        Company company = companyService.getCompany(principal);
        Notification notification = notificationService.findByID(notificationID);
        if (!notification.getCompany().equals(company)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Notification does not belong to this company");
        }
        notificationService.markAsRead(notification);
        return ResponseEntity.ok().body("Notification marked as read");
    }
}
