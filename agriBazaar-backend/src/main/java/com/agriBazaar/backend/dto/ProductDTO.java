package com.agriBazaar.backend.dto;

import lombok.Data;

@Data
public class ProductDTO {
    public String name;
    public String description;
    public double price;
    public int quantity;
    public Long farmerId;
}
