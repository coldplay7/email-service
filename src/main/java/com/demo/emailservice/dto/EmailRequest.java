package com.demo.emailservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;
}
