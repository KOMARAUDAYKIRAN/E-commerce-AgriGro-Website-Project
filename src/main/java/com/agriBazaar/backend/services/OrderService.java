package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.*;
import com.agriBazaar.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order placeOrder(Long userId, List<Long> productIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (Long productId : productIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(1); // Default to 1
            item.setPriceAtOrder(product.getPrice());

            totalAmount += product.getPrice();
            orderItems.add(item);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
