package com.demo.emailservice.provider;

import com.demo.emailservice.exception.EmailException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailgunEmailProvider implements EmailProvider {
    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("Sending email using mail gun email provider.");
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            log.info("Sending email using mail gun email provider failed.");
            throw new EmailException("Failed sending from mail gun. ");
        }
    }
}
