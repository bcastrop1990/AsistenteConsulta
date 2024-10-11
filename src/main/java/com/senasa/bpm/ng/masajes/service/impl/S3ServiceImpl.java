package com.senasa.bpm.ng.masajes.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.senasa.bpm.ng.masajes.service.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {
    @Autowired
    private AmazonS3 s3Client;

    private final String bucketName = "neura-bucket-imagenes";

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        s3Client.putObject(bucketName, key, file.getInputStream(), metadata);

        return s3Client.getUrl(bucketName, key).toString();
    }
}
