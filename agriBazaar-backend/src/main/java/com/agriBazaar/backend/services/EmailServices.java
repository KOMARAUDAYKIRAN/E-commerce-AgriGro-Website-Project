package com.agriBazaar.backend.services;

import com.agriBazaar.backend.entities.PreOrder;
import com.agriBazaar.backend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void sendDeletedMail(List<String>emails, Product product){

        for(String email:emails){
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Deletion of preorder for "+product.getName());
            String messageBody = String.format(
                    "Dear Customer,\n\n" +
                            "We regret to inform you that your preorder for the product \"%s\" has been deleted.\n\n" +
                            "Product Details:\n" +
                            "Name: %s\n" +
                            "Price: ₹%.2f\n" +
                            "Harvest Date: %s\n\n" +
                            "If you have any questions or concerns, please contact us at support@agribazaar.com.\n\n" +
                            "Thank you for understanding.\n\n" +
                            "Best Regards,\n" +
                            "The AgriBazaar Team",
                    product.getName(),
                    product.getName(),
                    product.getExpectedPrice(),
                    product.getExpectedHarvestDate()
            );

            message.setText(messageBody);
            mailSender.send(message);
        }
    }

    public void sendHarvestDateChangeEmail(String customerEmail, String productName, String originalHarvestDate, String newHarvestDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customerEmail);
        message.setSubject("Update: Harvest Time for Your Preorder");

        String emailBody = String.format(
                "Dear Customer,\n\n" +
                        "We wanted to inform you that the harvest time for your preorder of \"%s\" has been updated. " +
                        "Depending on the change, the harvest may be earlier or later than originally anticipated. Here are the details:\n\n" +
                        "Original Harvest Date: %s\n" +
                        "New Harvest Date: %s\n\n" +
                        "If the new harvest time affects your plans, please don't hesitate to reach out for further assistance or adjustments. " +
                        "We strive to provide you with the best service and will notify you if there are any further updates.\n\n" +
                        "Thank you for your patience and understanding. We appreciate your support and look forward to fulfilling your preorder.\n\n" +
                        "Best Regards,\n" +
                        "AgriBazaar Team\n" ,
                productName, originalHarvestDate, newHarvestDate);

        message.setText(emailBody);
        mailSender.send(message);
    }
}
