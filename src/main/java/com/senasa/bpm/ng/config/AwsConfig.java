package com.senasa.bpm.ng.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA47CR3GFKHSSTA7XD", "OVSS6R2l6xg52gI8LBs/OogukhVvhCpJIDmGnO9q");

        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_2) // Cambia a la región us-east-2
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
