package com.xyz.jdbnk.services;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerifyEmailService
{
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    public void verifyEmail(final String email) {
        try {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials
                            (this.accessKey, this.secretKey))).withRegion(Regions.US_EAST_1).build();
            VerifyEmailIdentityRequest emailIdentityRequest = new VerifyEmailIdentityRequest()
                    .withEmailAddress(email);
            client.verifyEmailIdentity(emailIdentityRequest);
            System.out.println("Verification Email sent!");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}