package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageURL;
    private String name;
    private String description;
    private double price;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private User farmer;
}

