package com.agriBazaar.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter

public class Bid {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double bidAmount;

    private double highestBid;

    private LocalDateTime bidTime;


    @ManyToOne
    @JoinColumn(name="auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Long getHighestBidderId(){
        return user!=null?user.getId():null;
    }


    public String getHighestBidder(){
        return user!=null?user.getUsername():null;
    }
}
