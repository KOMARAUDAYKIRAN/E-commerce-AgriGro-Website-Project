package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.Cart;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendCartReminder(Long userId, Cart cart){
        // ✅ Add email or push notification logic
        System.out.println("Reminder sent to user " + userId + " for cart ID " + cart.getId());
    }
}
