package com.agriBazaar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agriBazaar.backend.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
