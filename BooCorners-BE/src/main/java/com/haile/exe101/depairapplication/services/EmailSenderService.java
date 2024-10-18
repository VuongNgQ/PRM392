package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.services.interfaces.IEmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSenderService implements IEmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public String buildEmailContent(String orderTrackingNumber, String deliveryDate, StringBuilder productsHtml) {
        Context context = new Context();
        context.setVariable("orderTrackingNumber", orderTrackingNumber);
        context.setVariable("deliveryDate", deliveryDate);
        context.setVariable("productsHtml", productsHtml.toString());

        return templateEngine.process("email-template", context);
    }

    @Override
    public void sendMail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(new InternetAddress("boocorners@gmail.com"));

        helper.setTo(to);

        helper.setSubject(subject);

        helper.setText(body, true);

        // Send the email
        mailSender.send(message);
    }
}
