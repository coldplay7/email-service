package com.demo.emailservice.provider;

import com.demo.emailservice.exception.EmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SendGridEmailProviderTest {

    private SendGridEmailProvider provider;

    @BeforeEach
    void setUp() {
        provider = new SendGridEmailProvider(
                "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",
                "clashpeace7@gmail.com");
    }

    @Test
    void happyCase() {
        assertDoesNotThrow(() -> provider.sendEmail("clashpeace7@gmail.com",
                "test-subject",
                "test-body"));
    }

    @Test
    void emptyToEmail_throwsException() {
        EmailException e = assertThrows(EmailException.class, () -> provider.sendEmail("",
                "test-subject",
                "test-body"));
        assertEquals("Send Grid failed to send mail. <Does not contain a valid address.>",
                e.getMessage());
    }

    @Test
    void emptyBody_throwsException() {
        EmailException e = assertThrows(EmailException.class, () -> provider.sendEmail("foo@example.com",
                "test-subject",
                ""));
        assertEquals("Send Grid failed to send mail. <The content value must be a string at least one character in length.>",
                e.getMessage());
    }

}