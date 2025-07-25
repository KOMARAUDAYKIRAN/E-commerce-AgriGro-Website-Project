package com.agriBazaar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agriBazaar.backend.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
