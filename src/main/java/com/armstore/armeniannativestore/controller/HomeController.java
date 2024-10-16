package com.armstore.armeniannativestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/home", "/homepage"})
    public String homePage() {
        return "index";
    }
}
