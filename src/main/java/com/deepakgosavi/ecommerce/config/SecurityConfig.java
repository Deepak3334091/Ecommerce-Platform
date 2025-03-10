package com.deepakgosavi.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("deepak")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("Alex")
                .password(passwordEncoder().encode("user123"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/users").permitAll() // Allow everyone to see all users
                        .requestMatchers(HttpMethod.GET, "/users/email/{email}").authenticated() // Only authenticated users
                        .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("CUSTOMER", "ADMIN") // Customers & Admins can create users
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("ADMIN") // Only Admins can update
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN") // Only Admins can delete
                        .anyRequest().authenticated()) // Any other request needs authentication
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
