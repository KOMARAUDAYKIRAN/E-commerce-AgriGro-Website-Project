package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.Cart;
import com.agriBazaar.backend.entities.CartItem;
import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.CartItemRepository;
import com.agriBazaar.backend.repositories.CartRepository;
import com.agriBazaar.backend.repositories.ProductRepository;
import com.agriBazaar.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Cart addItemToCart(Long userId,Long productId, int quantity){
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getItems().add(cartItem);
        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }
 
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);  
                });
    }


    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
