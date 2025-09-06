package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid,Long> {
    List<Bid> findByAuctionIdOrderByBidAmountDesc(Long auctionId);
}
