package com.demo.emailservice.controller;

import com.demo.emailservice.dto.EmailRequest;
import com.demo.emailservice.dto.Problem;
import com.demo.emailservice.exception.EmailException;
import com.demo.emailservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmailServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailService service;

    @Test
    @SneakyThrows
    void happyPath() {
        mockMvc.perform(post("/send-email")
                .content(content())
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void internalServerError() {
        doThrow(EmailException.class).when(service).sendEmail(anyString(), anyString(), anyString());
        mockMvc.perform(post("/send-email")
                .content(content())
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isInternalServerError());
    }

    @Test
    @SneakyThrows
    void badRequest() {
        MvcResult result = mockMvc.perform(post("/send-email")
                        .content(new ObjectMapper()
                                .writeValueAsBytes(EmailRequest.builder().to("xxx").build()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Problem> problems = new ObjectMapper().readerForListOf(Problem.class).readValue(contentAsString);
        assertTrue(problems.stream()
                .map(Problem::getField).toList()
                .containsAll(List.of("to", "subject", "body")));
        assertTrue(problems.stream()
                .map(Problem::getMessage)
                .toList().containsAll(List.of(
                        "must not be blank",
                        "must be a well-formed email address")));
    }

    @SneakyThrows
    private byte[] content() {
        return new ObjectMapper().writeValueAsBytes(EmailRequest.builder()
                .to("test@foo.com")
                .body("test body")
                .subject("test subject")
                .build());
    }

}