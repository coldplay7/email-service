package com.demo.emailservice.provider;

import com.demo.emailservice.dto.SendGridError;
import com.demo.emailservice.dto.SendGridResponse;
import com.demo.emailservice.exception.EmailException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static com.sendgrid.Method.POST;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.resolve;

@Slf4j
@AllArgsConstructor
public class SendGridEmailProvider implements EmailProvider {

    private final String apiKey;
    private final String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("Trying to send email using send grid provider.");
        Mail mail = new Mail(new Email(fromEmail),
                subject,
                new Email(to),
                new Content("text/plain", body));
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            handleResponse(response);
            log.info("Send grid provider succeeded to send Email.");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EmailException("Send Grid failed to send mail. <" + ex.getMessage() + ">");
        }
    }

    @SneakyThrows
    private void handleResponse(Response response) {
        if (requireNonNull(resolve(response.getStatusCode())).isError()) {
            throw new EmailException(new ObjectMapper()
                    .readValue(response.getBody(), SendGridResponse.class)
                    .getErrors()
                    .stream()
                    .findFirst()
                    .map(SendGridError::getMessage)
                    .orElse(""));
        }
    }

}
