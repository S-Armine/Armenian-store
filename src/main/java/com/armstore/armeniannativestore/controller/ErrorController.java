package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.auth.AuthenticationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping
    public String error(Model model) {
        String home;
        if (!AuthenticationUtils.isAuthenticated()) {
            home = "/home";
        } else if (AuthenticationUtils.isCustomer()) {
            home = "/customer/home";
        } else {
            home = "/company/home";
        }
        model.addAttribute("home", home);
        return "error";
    }
}
