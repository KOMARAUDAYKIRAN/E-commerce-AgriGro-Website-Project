package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.PreOrder;
import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PreOrderRepository extends JpaRepository<PreOrder,Long> {
    long countByProduct_Id(Long productId);

    void deleteByProduct_IdAndBuyer_Id(Long productId, Long buyerId);

    boolean existsByProduct_IdAndBuyer_Id(Long productId, Long buyerId);


    List<PreOrder> findByProduct_ExpectedHarvestDate(LocalDate tomorrow);
}
