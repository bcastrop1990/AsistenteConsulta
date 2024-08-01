package com.senasa.bpm.ng.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSesConfig {

    @Bean
    public AmazonSimpleEmailService sesClient() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(
                "AKIA47CR3GFKCZKFEPTE", // Reemplaza con tu AWS Access Key ID real
                "tbTTKya2VawhPy+3aXydE0W+x7nQKnvUpHAgDP12" // Reemplaza con tu AWS Secret Access Key real
        );

        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.US_EAST_2) // Ajusta a la región donde está configurado SES
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}

