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

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService; // ✅ For reminders

    /**
     * ✅ Add item to cart and update lastUpdated timestamp.
     */
    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cart.getItems().add(cartItem);
        cart.setAbandoned(false); // Reset abandoned status
        cart.setLastUpdated(LocalDateTime.now());

        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

    /**
     * ✅ Get cart for user or create new if doesn't exist.
     */
    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setLastUpdated(LocalDateTime.now());
            return cartRepository.save(cart);
        }
        return cart;
    }

    /**
     * ✅ Remove item from cart.
     */
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // ==================== SMART CART RECOVERY ====================

    /**
     * ✅ Get all abandoned carts for a specific user.
     */
    public List<Cart> getAbandonedCartsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findAbandonedCartsByUser(user);
    }

    /**
     * ✅ Get all abandoned carts for admin dashboard.
     */
    public List<Cart> getAllAbandonedCarts() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        return cartRepository.findAbandonedCarts(cutoff);
    }

    /**
     * ✅ Send reminder to a specific user.
     */
    public void sendReminderForUser(Long userId) {
        List<Cart> carts = getAbandonedCartsByUser(userId);
        for (Cart cart : carts) {
            notificationService.sendCartReminder(userId, cart);
        }
    }

    /**
     * ✅ Detect and mark inactive carts as abandoned.
     * This runs automatically via a scheduler every 24 hours.
     */
    public void checkAbandonedCarts() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(24);
        List<Cart> inactiveCarts = cartRepository.findInactiveCarts(threshold);

        for (Cart cart : inactiveCarts) {
            cart.setAbandoned(true);
            cartRepository.save(cart);
            notificationService.sendCartReminder(cart.getUser().getId(), cart);
        }
    }
}
