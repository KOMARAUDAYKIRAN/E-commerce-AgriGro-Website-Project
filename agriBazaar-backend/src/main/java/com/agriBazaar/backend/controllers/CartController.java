package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.entities.Cart;
import com.agriBazaar.backend.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId,@RequestParam Long productId, @RequestParam int quantity){
        Cart updatedCart = cartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId){
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
