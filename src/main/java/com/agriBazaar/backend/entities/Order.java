package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }

    // 👇 This gives you access to products indirectly
    public List<Product> getProducts() {
        return orderItems.stream()
                .map(OrderItem::getProduct)
                .collect(Collectors.toList());
    }
}
