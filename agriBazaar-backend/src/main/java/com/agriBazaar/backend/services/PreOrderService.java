package com.agriBazaar.backend.services;


import com.agriBazaar.backend.entities.PreOrder;
import com.agriBazaar.backend.repositories.PreOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreOrderService {

    @Autowired
    private PreOrderRepository repository;

    @Autowired
    private EmailServices emailServices;

    public PreOrder createPreOrder(PreOrder preOrder){
        PreOrder saved=repository.save(preOrder);
        emailServices.sendConfirmationEmail(saved);

        return saved;
    }
}
