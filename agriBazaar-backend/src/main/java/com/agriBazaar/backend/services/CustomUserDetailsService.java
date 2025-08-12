package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // Add role-based authority
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        
        // Add additional authorities based on role
        switch (user.getRole()) {
            case "ADMIN":
                authorities.add(new SimpleGrantedAuthority("ADMIN_READ"));
                authorities.add(new SimpleGrantedAuthority("ADMIN_WRITE"));
                authorities.add(new SimpleGrantedAuthority("USER_READ"));
                authorities.add(new SimpleGrantedAuthority("USER_WRITE"));
                break;
            case "FARMER":
                authorities.add(new SimpleGrantedAuthority("PRODUCT_WRITE"));
                authorities.add(new SimpleGrantedAuthority("USER_READ"));
                break;
            case "SUPPLIER":
                authorities.add(new SimpleGrantedAuthority("PRODUCT_WRITE"));
                authorities.add(new SimpleGrantedAuthority("USER_READ"));
                break;
            case "BUYER":
                authorities.add(new SimpleGrantedAuthority("USER_READ"));
                break;
        }
        
        return authorities;
    }
}