package com.agriBazaar.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Public pages - accessible to everyone
                .requestMatchers("/", "/login", "/register", "/products", "/products/**", 
                               "/community", "/news", "/market-prices", "/zero-waste").permitAll()
                
                // Static resources
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                
                // API endpoints for public access
                .requestMatchers("/api/products/**").permitAll()
                
                // Admin endpoints
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                
                // User-specific endpoints
                .requestMatchers("/profile", "/orders", "/cart", "/api/cart/**", "/api/orders/**").authenticated()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf
                // Disable CSRF for API endpoints
                .ignoringRequestMatchers("/api/**")
            )
            .headers(headers -> headers
                .frameOptions().sameOrigin() // Allow frames from same origin
            );

        return http.build();
    }
}