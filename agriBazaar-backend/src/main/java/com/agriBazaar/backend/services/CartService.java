package com.agriBazaar.backend.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agriBazaar.backend.entities.Cart;
import com.agriBazaar.backend.entities.CartItem;
import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.CartItemRepository;
import com.agriBazaar.backend.repositories.CartRepository;
import com.agriBazaar.backend.repositories.ProductRepository;
import com.agriBazaar.backend.repositories.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findAll().stream()
                .filter(cart -> cart.getUser().getId().equals(userId))
                .findFirst()
                .orElseGet(() -> createCartForUser(user));
    }

    public Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalAmount(0.0);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<CartItem> existingItem = cart.getCartItems() == null ? Optional.empty() : cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setPrice(product.getPrice() * item.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice() * quantity);
            item.setCart(cart);
            cartItemRepository.save(item);
            cart.getCartItems().add(item);
        }
        updateCartTotal(cart);
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        if (cart.getCartItems() == null) {
            throw new RuntimeException("Cart is empty");
        }
        CartItem item = cart.getCartItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found in cart"));
        item.setQuantity(quantity);
        item.setPrice(item.getProduct().getPrice() * quantity);
        cartItemRepository.save(item);
        updateCartTotal(cart);
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        if (cart.getCartItems() == null) {
            throw new RuntimeException("Cart is empty");
        }
        CartItem item = cart.getCartItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);
        updateCartTotal(cart);
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {
        double total = cart.getCartItems() == null ? 0.0 : cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum();
        cart.setTotalAmount(total);
    }
}
