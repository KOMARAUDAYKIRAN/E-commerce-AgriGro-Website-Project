package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity

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

    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void setPrice(double price){
        this.price=price;
    }

    public void setStock(int stock){
        this.stock=stock;
    }

    public void setId(Long id){
        this.id=id;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        return price;
    }
    public int getStock(){
        return stock;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}





