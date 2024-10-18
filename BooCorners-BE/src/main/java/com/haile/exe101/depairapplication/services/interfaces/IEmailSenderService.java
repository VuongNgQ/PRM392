package com.haile.exe101.depairapplication.services.interfaces;

import jakarta.mail.MessagingException;

public interface IEmailSenderService {
    String buildEmailContent(String orderTrackingNumber, String deliveryDate, StringBuilder productsHtml);
    void sendMail(String to, String subject, String body) throws MessagingException;
}
