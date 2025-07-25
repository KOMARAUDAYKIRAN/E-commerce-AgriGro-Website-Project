package com.agriBazaar.backend.dto;

import lombok.Data;

import java.util.List;
@Data
public class PlaceOrderRequest {
    private Long userId;
    private List<Long> productIds;

    // Getters & Setters
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<Long> getProductIds() {
        return productIds;
    }
    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
