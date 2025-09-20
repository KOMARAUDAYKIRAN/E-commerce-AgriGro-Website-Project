package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private Double expectedPrice;


    private LocalDate expectedHarvestDate;

    private LocalDateTime uploadTime=LocalDateTime.now();

    @Column(name = "image_url") 
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private User farmer;

}





