package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.Order;
import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.OrderRepository;
import com.agriBazaar.backend.repositories.ProductRepository;
import com.agriBazaar.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Order placeOrder(Long userId, List<Long> productIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> products = productRepository.findAllById(productIds);

        double totalAmount = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}

