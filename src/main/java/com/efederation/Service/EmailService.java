package com.efederation.Service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String to, String from, String text);
    void sendEmailVerification(String to, String from) throws MessagingException;
}
