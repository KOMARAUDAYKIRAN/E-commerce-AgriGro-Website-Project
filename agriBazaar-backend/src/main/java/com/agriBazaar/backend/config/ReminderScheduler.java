package com.agriBazaar.backend.config;

import com.agriBazaar.backend.entities.PreOrder;
import com.agriBazaar.backend.repositories.PreOrderRepository;
import com.agriBazaar.backend.services.EmailServices;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {
    private final PreOrderRepository preOrderRepository;

    private final EmailServices emailServices;

    @Scheduled(cron = "0 20 18 * * ?",zone = "Asia/Kolkata")
    public void sendHarvestReminders(){
        LocalDate tomorrow=LocalDate.now().plusDays(1);

        List<PreOrder> preOrders=preOrderRepository.findByProduct_ExpectedHarvestDate(tomorrow);

        for(PreOrder preOrder:preOrders){
            if(preOrder.getBuyer()!=null && preOrder.getBuyer().getEmail()!=null){
                emailServices.sendReminder(
                        preOrder.getBuyer().getEmail(),
                        preOrder.getProduct().getName(),
                        preOrder.getProduct().getExpectedHarvestDate().toString()
                );
            }
        }
    }
}
