package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
