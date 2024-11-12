package com.efederation.Service.impl;

import com.efederation.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String from, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendEmailVerification(String to, String from) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Your efederation account needs to be verified");

        String html = "<!doctype html>\n" +
                "<html lang=\"en\"> \n" +
                "<head> \n" +
                "<title>email</title>\n" +
                "</head> \n" +
                "<body>\n" +
                "<span>We are excited to have you join us. Please press " +
                "<a href=\"http://ec2-3-144-245-126.us-east-2.compute.amazonaws.com:8080/api/v1/auth/validate/%s\">\n" +
                "here</a> to validate your email. After, head back to the app to login and begin your journey.</span></body>\n" +
                "</html>";
        String htmlWithEmail = String.format(html, to);
        helper.setText(htmlWithEmail, true);
        helper.setTo(to);
        helper.setFrom(from);
        emailSender.send(message);
    }

}
