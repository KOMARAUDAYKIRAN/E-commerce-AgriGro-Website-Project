package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.entities.CartItem;
import com.agriBazaar.backend.services.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;
    @GetMapping("/getAllItems")
    public List<CartItem> getAllCartItems(){
        return cartItemService.getAllCartItems();
    }

    @GetMapping("/get/{id}")
    public CartItem getCartItemById(@PathVariable Long id){
        return cartItemService.getCartItemById(id).orElse(null);
    }

    @PostMapping("/add")
    public CartItem addCartItem(@RequestBody CartItem cartItem){
        return cartItemService.addCartItem(cartItem);
    }

    @PutMapping("update/{id}")
    public CartItem updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem){
        return cartItemService.updateCartItem(id, cartItem);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCartItem(@PathVariable Long id){
        cartItemService.deleteCartItem(id);
    }
}
