package com.agriBazaar.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String title;
    private double startingPrice;
    private double highestBid;
    private String highestBidder;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String imageUrl;


    @OneToMany(mappedBy = "auction",cascade = {CascadeType.ALL},orphanRemoval = true)
    @JsonIgnore
    List<Bid> bids=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToOne
    private User winner;

    public Long getOwnerId(){
        return owner!=null?owner.getId():null;
    }

    public String getHighestBidder() {
        return bids.stream()
                .max((b1, b2) -> Double.compare(b1.getBidAmount(), b2.getBidAmount()))
                .map(b -> b.getUser().getUsername())
                .orElse("No bids yet");
    }


    public Long getHighestBidderId() {
        return bids.stream()
                .max((b1, b2) -> Double.compare(b1.getBidAmount(), b2.getBidAmount()))
                .map(b -> b.getUser().getId())
                .orElse(null);
    }
}
