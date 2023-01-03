package com.demo.emailservice.dto;

import lombok.Data;

@Data
public class SendGridError {
    private String message;
    private String field;
    private String help;
}
