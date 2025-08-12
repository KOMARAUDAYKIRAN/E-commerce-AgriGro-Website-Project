package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.entities.Product;
import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.services.ProductService;
import com.agriBazaar.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    /**
     * Home page
     */
    @GetMapping("/")
    public String index(Model model) {
        // Get featured products (first 8 products)
        List<Product> featuredProducts = productService.getAllProducts()
                .stream()
                .limit(8)
                .collect(Collectors.toList());
        
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("pageTitle", "Home");
        return "index";
    }

    /**
     * Products listing page with search and filter
     */
    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Model model) {
        
        // Get all products
        List<Product> allProducts = productService.getAllProducts();
        
        // Apply filters
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> {
                    // Category filter
                    if (category != null && !category.isEmpty()) {
                        return product.getName().toLowerCase().contains(category.toLowerCase()) ||
                               product.getDescription().toLowerCase().contains(category.toLowerCase());
                    }
                    return true;
                })
                .filter(product -> {
                    // Search filter
                    if (search != null && !search.isEmpty()) {
                        return product.getName().toLowerCase().contains(search.toLowerCase()) ||
                               product.getDescription().toLowerCase().contains(search.toLowerCase());
                    }
                    return true;
                })
                .collect(Collectors.toList());
        
        model.addAttribute("products", filteredProducts);
        model.addAttribute("category", category);
        model.addAttribute("searchQuery", search);
        model.addAttribute("pageTitle", "Products");
        
        return "products";
    }

    /**
     * Product detail page
     */
    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        return productService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("pageTitle", product.getName());
                    return "product-detail";
                })
                .orElse("redirect:/products");
    }

    /**
     * Login page
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                       @RequestParam(required = false) String message,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }
        if (message != null) {
            model.addAttribute("message", message);
        }
        model.addAttribute("pageTitle", "Login");
        return "auth/login";
    }

    /**
     * Registration page
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Register");
        return "auth/register";
    }

    /**
     * Handle registration form submission
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user,
                              BindingResult bindingResult,
                              @RequestParam String confirmPassword,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Register");
            return "auth/register";
        }
        
        // Check password confirmation
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("pageTitle", "Register");
            return "auth/register";
        }
        
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("message", 
                "Registration successful! Please login with your credentials.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("pageTitle", "Register");
            return "auth/register";
        }
    }

    /**
     * Community forum page
     */
    @GetMapping("/community")
    public String community(Model model) {
        model.addAttribute("pageTitle", "Community Forum");
        return "community";
    }

    /**
     * News page
     */
    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("pageTitle", "Agricultural News");
        return "news";
    }

    /**
     * Market prices page
     */
    @GetMapping("/market-prices")
    public String marketPrices(Model model) {
        model.addAttribute("pageTitle", "Market Prices");
        return "market-prices";
    }
}