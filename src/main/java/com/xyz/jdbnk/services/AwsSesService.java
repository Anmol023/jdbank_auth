package com.xyz.jdbnk.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.xyz.jdbnk.exception.AwsSesClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AwsSesService {

    private static final String CHAR_SET = "UTF-8";
    private static final String SUBJECT = "Onboard Process Completed";
    private final AmazonSimpleEmailService emailService;
    private final String sender;

    @Autowired
    public AwsSesService(AmazonSimpleEmailService emailService,
                         @Value("${email.sender}") String sender) {
        this.emailService = emailService;
        this.sender = sender;
    }

    public void sendEmail(String email, String body) {
        try {

            // The time for request/response round trip to aws in milliseconds
            int requestTimeout = 3000;

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(email))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset(CHAR_SET).withData(body)))
                            .withSubject(new Content()
                                    .withCharset(CHAR_SET).withData(SUBJECT)))
                    .withSource(sender).withSdkRequestTimeout(requestTimeout);
            emailService.sendEmail(request);
        } catch (RuntimeException e) {
            throw new AwsSesClientException("Failed to send email ", e);
        }
    }
}