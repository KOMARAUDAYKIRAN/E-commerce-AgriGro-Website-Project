package com.agriBazaar.backend.services;


import com.agriBazaar.backend.entities.PreOrder;
import com.agriBazaar.backend.repositories.PreOrderRepository;
import com.agriBazaar.backend.repositories.ProductRepository;
import com.agriBazaar.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreOrderService {

    @Autowired
    private PreOrderRepository repository;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public PreOrder createPreOrder(PreOrder preOrder){
        if(preOrder.getProduct()!=null && preOrder.getProduct().getId()!=null){
            preOrder.setProduct(
                    productRepository.findById(preOrder.getProduct().getId())
                            .orElseThrow(()->new RuntimeException("Product not found"))
            );
        }

        if(preOrder.getBuyer()!=null && preOrder.getBuyer().getId()!=null){
            preOrder.setBuyer(
                    userRepository.findById(preOrder.getBuyer().getId())
                            .orElseThrow(()->new RuntimeException("Buyer not found"))
            );

            preOrder.setBuyerEmail(preOrder.getBuyer().getEmail());
        }


        PreOrder saved=repository.save(preOrder);
        emailServices.sendConfirmationEmail(saved);
        return saved;
    }

    public List<PreOrder> getAllPreorder(){
        return repository.findAll();
    }

    public Optional<PreOrder> getPreorderById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public void cancelPreorder(Long productId,Long buyerId){
        if(!repository.existsByProduct_IdAndBuyer_Id(productId,buyerId)){
            throw new IllegalArgumentException("Preorder not found for this user");
        }
        repository.deleteByProduct_IdAndBuyer_Id(productId,buyerId);
    }
}
