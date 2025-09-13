package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.repositories.ProductRepository;
import com.agriBazaar.backend.repositories.UserRepository;
import com.agriBazaar.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
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

    @PostMapping(value = "/preOrderUpload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> uploadProduct(
            @RequestParam String name,
            @RequestParam Double expectedPrice,
            @RequestParam("expectedHarvestDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate harvestDate,
            @RequestParam String description,
            @RequestParam("image")MultipartFile image,
            @RequestParam("userId")Long userId
            ) throws Exception{
        String filename=System.currentTimeMillis()+"_"+image.getOriginalFilename();
        Path uploadPath= Paths.get("uploads");
        if(!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        Path filePath=uploadPath.resolve(filename);
        Files.copy(image.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        Product product=new Product();
        product.setName(name);
        product.setExpectedPrice(expectedPrice);
        product.setExpectedHarvestDate(harvestDate);
        product.setImageUrl("http://localhost:8083/uploads/"+filename);
        product.setDescription(description);
        product.setFarmer(userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found")));
        product.setPrice(expectedPrice);

        Product saved=productRepository.save(product);

        return ResponseEntity.ok(saved);
    }
}

