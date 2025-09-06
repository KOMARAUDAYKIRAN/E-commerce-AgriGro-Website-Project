package com.agriBazaar.backend.controllers;


import com.agriBazaar.backend.entities.Auction;
import com.agriBazaar.backend.entities.Bid;
import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.AuctionRepository;
import com.agriBazaar.backend.repositories.UserRepository;
import com.agriBazaar.backend.services.AuctionService;
import com.agriBazaar.backend.services.BidService;
import com.zaxxer.hikari.util.UtilityElf;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auctions")
@CrossOrigin(origins = "http://localhost:63342/E-commerce-AgriGro-Website-Project4/agriBazzar-frontend/auction.html")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;
    private final BidService bidService;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @GetMapping("/getAll")
    public List<Auction> getAllAuctions(){
        return auctionService.getAllAuctions();
    }

    @PostMapping("/upload")
    public Auction createAuction(@RequestParam("title") String title,
                                 @RequestParam("startingPrice") double initialValue,
                                 @RequestParam("imageFile")MultipartFile imageFile,
                                 @RequestParam("userId" )Long userId) throws IOException {
        User user=userRepository.findById(userId).orElse(null);
        String fileName=System.currentTimeMillis()+"-"+imageFile.getOriginalFilename();
        Path path= Paths.get("uploads/",fileName);
        Files.createDirectories(path.getParent());
        Files.write(path,imageFile.getBytes());

        Auction auction=new Auction();
        auction.setTitle(title);
        auction.setStartingPrice(initialValue);
        auction.setHighestBid(initialValue);
        auction.setOwner(user);
        auction.setImageUrl("/uploads/"+fileName);

        return auctionService.createAuction(auction);
    }

    @PostMapping("/bid/{id}")
    public Bid placeBid(@PathVariable Long id, @RequestBody Map<String,Object> payload){
        Long userId=Long.parseLong(payload.get("userId").toString());
        double amount=Double.parseDouble(payload.get("bidAmount").toString());

        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        return bidService.placeBids(id,userId,amount,user);
    }

    @GetMapping("/auctions/won/{userId}")
    public List<Auction> getWonAuction(@PathVariable Long userId){
        return auctionRepository.findByWinnerIdAndStatus(userId,"WON");
    }
}
