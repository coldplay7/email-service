package com.demo.emailservice.service;

import com.demo.emailservice.exception.EmailException;
import com.demo.emailservice.provider.MailgunEmailProvider;
import com.demo.emailservice.provider.SendGridEmailProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class EmailServiceImplTest {

    private EmailServiceImpl service;
    @Mock
    private SendGridEmailProvider sendGridEmailProvider;
    @Mock
    private MailgunEmailProvider mailgunEmailProvider;

    @BeforeEach
    void setUp() {
        openMocks(this);
        service = new EmailServiceImpl(of(sendGridEmailProvider, mailgunEmailProvider));
    }

    @Test
    void sendEmail_successFromProvider() {
        assertDoesNotThrow(() -> service.sendEmail(anyString(), anyString(), anyString()));
        verify(sendGridEmailProvider, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(mailgunEmailProvider, times(0)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void sendEmail_failFromSendGridSuccessFromMailGun() {
        doThrow(EmailException.class).when(sendGridEmailProvider).sendEmail(anyString(), anyString(), anyString());

        assertDoesNotThrow(() -> service.sendEmail(anyString(), anyString(), anyString()));
        verify(sendGridEmailProvider, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(mailgunEmailProvider, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void sendEmail_failFromMailGunSuccessFromSendGrid() {
        doThrow(EmailException.class).when(mailgunEmailProvider).sendEmail(anyString(), anyString(), anyString());

        assertDoesNotThrow(() -> service.sendEmail(anyString(), anyString(), anyString()));
        verify(sendGridEmailProvider, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void sendEmail_bothProviderFailsThrowsException() {
        doThrow(EmailException.class).when(sendGridEmailProvider).sendEmail(anyString(), anyString(), anyString());
        doThrow(EmailException.class).when(mailgunEmailProvider).sendEmail(anyString(), anyString(), anyString());

        assertThrows(EmailException.class, () -> service.sendEmail(anyString(), anyString(), anyString()));
        verify(sendGridEmailProvider, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(mailgunEmailProvider, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

}