package com.agriBazaar.backend.controllers;


import com.agriBazaar.backend.entities.PreOrder;
import com.agriBazaar.backend.services.PreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preorders")
@CrossOrigin(origins = "*") //allowing front end calls from any origin
public class PreOrderController {

    @Autowired
    private PreOrderService service;

    @PostMapping
    public ResponseEntity<PreOrder> submitPreorder(@RequestBody  PreOrder preOrder){
        PreOrder saved= service.createPreOrder(preOrder);
        return ResponseEntity.ok(saved);
    }
}
