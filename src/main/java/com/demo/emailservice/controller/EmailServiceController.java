package com.demo.emailservice.controller;

import com.demo.emailservice.dto.EmailRequest;
import com.demo.emailservice.exception.EmailException;
import com.demo.emailservice.service.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
public class EmailServiceController {
    private final EmailService service;

    @PostMapping(value = "/send-email")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest request) {
        try {
            service.sendEmail(request.getTo(), request.getSubject(), request.getBody());
            return ok().body("Email sent successfully to " + request.getTo());
        } catch (EmailException e) {
            return status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
