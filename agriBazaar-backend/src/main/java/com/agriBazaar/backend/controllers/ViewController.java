package com.agriBazaar.backend.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ViewController {

    @GetMapping("/products")
    public String viewProducts() {
        return "products";
    }
}
