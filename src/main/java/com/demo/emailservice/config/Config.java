package com.demo.emailservice.config;

import com.demo.emailservice.provider.EmailProvider;
import com.demo.emailservice.provider.MailgunEmailProvider;
import com.demo.emailservice.provider.SendGridEmailProvider;
import com.demo.emailservice.service.EmailService;
import com.demo.emailservice.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {
    @Bean
    EmailProvider sendGirdEmailProvider(@Value("${sendgrid-api-key-clash}") String apiKey,
                                        @Value("${from-email}") String fromEmail) {
        return new SendGridEmailProvider(apiKey, fromEmail);
    }

    @Bean
    EmailProvider mailGunEmailProvider() {
        return new MailgunEmailProvider();
    }

    @Bean
    EmailService emailService(List<EmailProvider> providers) {
        return new EmailServiceImpl(providers);
    }

}
