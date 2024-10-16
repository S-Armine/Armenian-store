package com.armstore.armeniannativestore.controller;

import com.armstore.armeniannativestore.auth.JwtUtils;
import com.armstore.armeniannativestore.service.ArmStoreUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final ArmStoreUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, ArmStoreUserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/customer")
    public String showCustomerLogin() {
        return "customer-login";
    }

    @GetMapping("/company")
    public String showCompanyLogin() {
        return "company-login";
    }

    @PostMapping("/customer")
    @ResponseBody
    public ResponseEntity<Void> loginCustomer(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) throws Exception {
        return getResponseEntity(loginRequest, response);
    }

    @PostMapping("/company")
    @ResponseBody
    public ResponseEntity<Void> loginCompany(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) throws Exception {
        return getResponseEntity(loginRequest, response);
    }

    private ResponseEntity<Void> getResponseEntity(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails customerDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtUtils.generateJwtToken(customerDetails);

        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }


}
