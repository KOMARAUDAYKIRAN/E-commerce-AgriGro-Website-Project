package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.CartItem;
import com.agriBazaar.backend.repositories.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems(){
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> getCartItemById(Long id){
        return cartItemRepository.findById(id);
    }

    public CartItem addCartItem(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(Long id, CartItem updatedItem){
        return cartItemRepository.findById(id).map(item ->{
            item.setQuantity(updatedItem.getQuantity());
            item.setCart(updatedItem.getCart());
            item.setProduct(updatedItem.getProduct());
            item.setPrice(updatedItem.getPrice());
            return cartItemRepository.save(item);
        }).orElse(null);
    }
    public void deleteCartItem(Long id){
        cartItemRepository.deleteById(id);
    }
}

