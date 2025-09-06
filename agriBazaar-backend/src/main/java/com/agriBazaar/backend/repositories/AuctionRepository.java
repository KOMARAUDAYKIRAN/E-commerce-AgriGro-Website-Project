package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
    List<Auction> findByWinnerIdAndStatus(Long userId, String won);
}
