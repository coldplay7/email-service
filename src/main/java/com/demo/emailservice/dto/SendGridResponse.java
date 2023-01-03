package com.demo.emailservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class SendGridResponse {
    private List<SendGridError> errors;

}

