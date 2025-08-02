package com.agriBazaar.backend.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter

@Entity
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String cropName;
    private LocalDate expectedHarvestDate;
    private boolean notified=false;
    private LocalDateTime createdAt=LocalDateTime.now();
}
