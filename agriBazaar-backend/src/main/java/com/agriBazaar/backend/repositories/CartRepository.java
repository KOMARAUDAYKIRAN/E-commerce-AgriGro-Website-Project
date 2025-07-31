package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Object findByUserId(Long userId);
}
