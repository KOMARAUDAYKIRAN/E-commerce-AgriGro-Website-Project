package com.agriBazaar.backend.entities;

import jakarta.persistence.*;

import java.util.List;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @Size(min=8)
    private String password;

    @NotBlank
    private String role; // BUYER, FARMER, ADMIN

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

   public void setName(String name){
        this.name=name;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setRole(String role){
        this.role=role;
    }
    
    public Long getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
}

