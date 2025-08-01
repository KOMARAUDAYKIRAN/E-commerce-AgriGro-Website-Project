package com.agriBazaar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agriBazaar.backend.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
