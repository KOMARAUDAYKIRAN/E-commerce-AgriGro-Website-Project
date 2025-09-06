package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.Auction;
import com.agriBazaar.backend.repositories.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionSchedulerService {
    private final AuctionRepository auctionRepository;
    private final AuctionService auctionService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void closedExpiredAuctions(){
        LocalDateTime now=LocalDateTime.now();

        List<Auction> auctions=auctionRepository.findAll().stream()
                .filter(a->"ONGOING".equals(a.getStatus()) && a.getEndTime()!=null && a.getEndTime().isBefore(now))
                .toList();

        for(Auction auction:auctions){
            auctionService.closeAuction(auction);
        }
    }
}
