package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.dto.ProductDTO;
import com.agriBazaar.backend.dto.ProductResponse;
import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.entities.Farmer;
import com.agriBazaar.backend.repositories.FarmerRepository;
import com.agriBazaar.backend.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FarmerRepository farmerRepository;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO dto) {
        Farmer farmer = farmerRepository.findById(dto.farmerId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Farmer not found with ID: " + dto.farmerId));

        Product product = new Product(dto.name, dto.description, dto.price, dto.quantity, farmer);
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(ProductResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
