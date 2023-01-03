package com.demo.emailservice.provider;

import com.demo.emailservice.exception.EmailException;

public interface EmailProvider {
    void sendEmail(String to, String subject, String body) throws EmailException;
}
