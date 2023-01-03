package com.demo.emailservice.service;

import com.demo.emailservice.exception.EmailException;

public interface EmailService {
    public void sendEmail(String to, String subject, String body) throws EmailException;
}
