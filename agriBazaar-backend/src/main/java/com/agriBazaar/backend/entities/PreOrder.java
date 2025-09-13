package com.agriBazaar.backend.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

@Entity
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Product product;

    @ManyToOne
    private User buyer;

    private String buyerEmail;
    private LocalDateTime createdAt=LocalDateTime.now();


}
