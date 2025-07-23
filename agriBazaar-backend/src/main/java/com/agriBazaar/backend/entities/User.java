package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role; // BUYER, FARMER, ADMIN

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}

