package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.dto.OrderSummaryResponse;
import com.agriBazaar.backend.dto.PlaceOrderRequest;
import com.agriBazaar.backend.entities.Order;
import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ GET all orders
    @GetMapping
    public List<OrderSummaryResponse> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(order -> new OrderSummaryResponse(
                        order.getId(),
                        order.getUser().getId(),
                        order.getProducts().stream()
                                .map(Product::getId)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    // ✅ DELETE order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    // ✅ POST place a new order
    @PostMapping
    public OrderSummaryResponse placeOrder(@RequestBody PlaceOrderRequest request) {
        Order order = orderService.placeOrder(request.getUserId(), request.getProductIds());

        List<Long> productIds = order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        return new OrderSummaryResponse(order.getId(), order.getUser().getId(), productIds);
    }
}
