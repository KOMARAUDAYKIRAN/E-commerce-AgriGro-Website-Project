package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.Auction;
import com.agriBazaar.backend.entities.Bid;
import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionService auctionService;

    public Bid placeBids(Long auctionId, Long userId, double amount, User user){
        Auction auction= auctionService.getAuction(auctionId);

        if(LocalDateTime.now().isAfter(auction.getEndTime())){
            throw new RuntimeException("Auction has ended");
        }

        if(amount<=auction.getHighestBid()){
            throw new RuntimeException("Bid must be higher than current highest bid");
        }


        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setUser(user);
        bid.setBidAmount(amount);
        bid.setBidTime(LocalDateTime.now());
        bid.setHighestBid(amount);

        auction.setHighestBidder(user.getUsername());
        auction.setHighestBid(amount);

        auctionService.save(auction);
        return bidRepository.save(bid);
    }

    public List<Bid> getBidsForAuction(Long auctionId){
        return bidRepository.findByAuctionIdOrderByBidAmountDesc(auctionId);
    }
}
