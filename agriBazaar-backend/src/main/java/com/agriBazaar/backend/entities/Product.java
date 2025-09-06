package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private double price;
    private int stock;

    @Column(name = "image_url") 
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private User farmer;

}





