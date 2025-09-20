package com.agriBazaar.backend.controllers;


import com.agriBazaar.backend.entities.PreOrder;
import com.agriBazaar.backend.repositories.PreOrderRepository;
import com.agriBazaar.backend.services.PreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preorders")
@CrossOrigin(origins = "http://localhost:63342/E-commerce-AgriGro-Website-Project5/agriBazzar-frontend/Preorder.html")
public class PreOrderController {

    @Autowired
    private PreOrderService service;

    @Autowired
    private PreOrderRepository preOrderRepository;

    @PostMapping("/submitPreorder")
    public ResponseEntity<?> submitPreorder(@RequestBody  PreOrder preOrder){
        Long productId=preOrder.getProduct().getId();

        long count=preOrderRepository.countByProduct_Id(productId);

        if(count>=5){
            return ResponseEntity
                    .badRequest()
                    .body("This product has reached maximum preorder limit(5).");
        }
        PreOrder saved= service.createPreOrder(preOrder);
        return ResponseEntity.ok(saved);
    }



    @GetMapping
    public List<PreOrder> getAllPreorders(){
        return service.getAllPreorder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreOrder> getPreorderById(@PathVariable Long id){
        return service.getPreorderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cancel/{productId}/{buyerId}")
    public ResponseEntity<?> cancelPreorder(@PathVariable Long productId,@PathVariable Long buyerId){

        try{
            service.cancelPreorder(productId,buyerId);
            return ResponseEntity.ok("Preorder cancelled successfully");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
