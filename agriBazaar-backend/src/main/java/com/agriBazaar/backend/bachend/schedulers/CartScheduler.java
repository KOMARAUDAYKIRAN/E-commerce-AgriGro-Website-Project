package com.agriBazaar.backend.schedulers;

import com.agriBazaar.backend.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CartScheduler {

    private final CartService cartService;

    // Run every 1 hour to detect abandoned carts
    @Scheduled(cron = "0 0 * * * *")
    public void runAbandonedCartCheck() {
        cartService.checkAbandonedCarts();
    }
}
