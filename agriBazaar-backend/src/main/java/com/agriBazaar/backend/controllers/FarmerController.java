package com.agriBazaar.backend.controllers;

import com.agriBazaar.backend.entities.Farmer;
import com.agriBazaar.backend.repositories.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    @Autowired
    private FarmerRepository farmerRepository;

    @PostMapping
    public Farmer createFarmer(@RequestBody Farmer farmer) {
        return farmerRepository.save(farmer);
    }

    @GetMapping
    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Farmer getFarmerById(@PathVariable Long id) {
        return farmerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Farmer not found"));
    }
}
