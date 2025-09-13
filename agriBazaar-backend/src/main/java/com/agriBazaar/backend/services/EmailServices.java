package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.PreOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(PreOrder order){
        if(order.getBuyerEmail()==null || order.getProduct()==null) return;

        String sub="Preorder confirmation-"+order.getProduct().getName();

        String message= "Hello,\n\n" +
                "Thank you for pre-ordering: " + order.getProduct().getName() + "\n" +
                "Expected Price: ₹" + order.getProduct().getExpectedPrice() + "\n" +
                "Expected Harvest Date: " + order.getProduct().getExpectedHarvestDate() + "\n\n" +
                "We will notify you again closer to harvest time.\n\n" +
                "Regards,\nAgriBazaar Team";

        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(order.getBuyerEmail());
        email.setSubject(sub);
        email.setText(message);
        mailSender.send(email);
    }

    public void sendReminder(String to,String productName,String harvestDate){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Harvest Reminder -"+productName);
        message.setText("Dear User,\n\nYour preordered product \"" + productName +
                "\" will be harvested on " + harvestDate +
                ". Please be prepared to proceed with payment tomorrow.\n\nAgriBazaar Team");
        mailSender.send(message);
    }
}
