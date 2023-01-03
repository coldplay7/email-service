package com.demo.emailservice.dto;

import lombok.Data;

@Data
public class Problem {
    private int httpStatusCode;
    private String message;
    private String field;
}
