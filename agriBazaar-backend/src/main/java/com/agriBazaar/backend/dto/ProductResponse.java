package com.agriBazaar.backend.dto;

import com.agriBazaar.backend.entities.Product;

public class ProductResponse {
    public Long id;
    public String name;
    public String description;
    public double price;
    public int quantity;
    public Long farmerId;
    public String farmerName;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.farmerId = product.getFarmer().getId();
        this.farmerName = product.getFarmer().getName(); // Optional, nice UX
    }
}
