package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}

