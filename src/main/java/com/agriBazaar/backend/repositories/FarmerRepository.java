package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
}
