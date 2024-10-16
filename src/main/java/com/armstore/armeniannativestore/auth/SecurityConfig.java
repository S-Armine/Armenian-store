package com.armstore.armeniannativestore.auth;

import com.armstore.armeniannativestore.service.ArmStoreUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ComponentScan(basePackageClasses = {ArmStoreUserDetailsService.class, JwtFilter.class})
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final ArmStoreUserDetailsService armStoreUserDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, ArmStoreUserDetailsService armStoreUserDetailsService) {
        this.jwtFilter = jwtFilter;
        this.armStoreUserDetailsService = armStoreUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/logout","/login/**",
                                "/registration/**", "/home", "/", "/homepage").permitAll()
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/company/**").hasRole("COMPANY")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement-> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(out->out.logoutUrl("/logout").logoutSuccessUrl("/home").deleteCookies("jwtToken"));
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(armStoreUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}