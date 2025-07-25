package com.agriBazaar.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
}
