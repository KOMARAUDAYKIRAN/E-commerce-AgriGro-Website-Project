package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.entities.Farmer;
import com.agriBazaar.backend.repositories.ProductRepository;
import com.agriBazaar.backend.repositories.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    public Product addProduct(Product product) {
        Long farmerId = product.getFarmer().getId();

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found with ID: " + farmerId));

        product.setFarmer(farmer);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setQuantity(updatedProduct.getQuantity());

                    if (updatedProduct.getFarmer() != null) {
                        Long farmerId = updatedProduct.getFarmer().getId();
                        Farmer farmer = farmerRepository.findById(farmerId)
                                .orElseThrow(() -> new RuntimeException("Farmer not found with ID: " + farmerId));
                        product.setFarmer(farmer);
                    }

                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
