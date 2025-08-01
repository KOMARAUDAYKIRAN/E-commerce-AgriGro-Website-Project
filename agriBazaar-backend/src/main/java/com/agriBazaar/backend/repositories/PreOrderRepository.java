package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.PreOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreOrderRepository extends JpaRepository<PreOrder,Long> {
}
