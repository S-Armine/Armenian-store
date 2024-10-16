package com.armstore.armeniannativestore.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    public static Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return getAuthentication() != null;
    }

    public static boolean isCustomer() {
        boolean authenticated = getAuthentication() != null;
        System.out.println("Is Authenticated: " + authenticated);
        if (isAuthenticated()) {
            System.out.println(getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER")));
            return getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
        }
        return false;
    }

    public static boolean isCompany() {
        if (isAuthenticated()) {
            return getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY"));
        }
        return false;
    }

}
