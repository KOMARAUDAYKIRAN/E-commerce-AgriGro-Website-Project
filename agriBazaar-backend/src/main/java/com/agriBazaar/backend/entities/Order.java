package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public void setUser(User user){
        this.user=user;
    }

    public void setProducts(List<Product> products){
        this.products=products;
    }

    public void setTotalAmount(double totalAmount){
        this.totalAmount=totalAmount;
    }
    public void setOrderDate(LocalDateTime time){
        this.orderDate=time;
    }
}

