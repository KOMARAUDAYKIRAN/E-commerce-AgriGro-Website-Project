package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

    public Product(String name, String description, double price, int quantity, Farmer farmer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.farmer = farmer;
    }
}