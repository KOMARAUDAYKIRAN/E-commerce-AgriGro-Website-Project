package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.Cart;
import com.agriBazaar.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // ✅ Find cart by User
    Cart findByUser(User user);

    // ✅ Find abandoned carts for a specific user
    @Query("SELECT c FROM Cart c WHERE c.user = :user AND c.isAbandoned = true")
    List<Cart> findAbandonedCartsByUser(User user);

    // ✅ Find inactive carts older than threshold (not yet marked abandoned)
    @Query("SELECT c FROM Cart c WHERE c.lastUpdated < :threshold AND c.isAbandoned = false")
    List<Cart> findInactiveCarts(LocalDateTime threshold);
}
