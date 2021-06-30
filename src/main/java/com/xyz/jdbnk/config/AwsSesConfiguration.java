package com.xyz.jdbnk.config;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AwsSesConfiguration {
    private final String region;

    public AwsSesConfiguration(@Value("${email.region}") String region) {
        this.region = region;
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(region).build();
    }
    public VerifyEmailIdentityResult verifyEmailIdentity(AmazonSimpleEmailService client, String emailAddress){
        VerifyEmailIdentityRequest emailIdentityRequest = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);
        return client.verifyEmailIdentity(emailIdentityRequest);
    }
}
