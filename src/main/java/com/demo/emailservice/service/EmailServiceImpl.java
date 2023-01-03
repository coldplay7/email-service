package com.demo.emailservice.service;

import com.demo.emailservice.exception.EmailException;
import com.demo.emailservice.provider.EmailProvider;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private List<EmailProvider> providers;

    public void sendEmail(String to, String subject, String body) throws EmailException {
        EmailException ex = null;
        for (EmailProvider provider : providers) {
            try {
                provider.sendEmail(to, subject, body);
                return;
            } catch (EmailException e) {
                ex = e;
            }
        }
        throw ex;
    }

}
