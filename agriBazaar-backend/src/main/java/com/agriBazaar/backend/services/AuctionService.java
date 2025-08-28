package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.Auction;
import com.agriBazaar.backend.entities.Bid;
import com.agriBazaar.backend.repositories.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public Auction createAuction(Auction auction){
        auction.setStartTime(LocalDateTime.now());
        auction.setEndTime(LocalDateTime.now().plusDays(1));
        auction.setStatus("ONGOING");
        return auctionRepository.save(auction);
    }

    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }

    public Auction getAuction(Long id){
        return auctionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Auction not found"));
    }

    public Auction save(Auction auction){
        return auctionRepository.save(auction);
    }

    @Transactional
    public void closeAuction(Auction auction){
        Optional<Bid> highestBid=auction.getBids().stream()
                .max(Comparator.comparingDouble(Bid::getBidAmount));

        if(highestBid.isPresent() && highestBid.get().getBidAmount()>=auction.getStartingPrice()){
            auction.setWinner(highestBid.get().getUser());
            auction.setHighestBid(highestBid.get().getBidAmount());
            auction.setStatus("WON");
        }
        else{
            auction.setStatus("EXPIRED");
        }
        auctionRepository.save(auction);
    }
}
