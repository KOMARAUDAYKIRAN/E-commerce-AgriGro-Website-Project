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
        String subject="Pre-order Confirmation "+order.getCropName();

        String message="Hello,\n\n"+
                "Thank you for pre-ordering *"+order.getCropName()+"*.\n"+
                "Expected harvest date:"+order.getExpectedHarvestDate()+"\n\n"+
                "We will notify you again closer to harvest time.\n\n"+
                "Regards, \nAgriSmart Team";

        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(order.getEmail());
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }
}
