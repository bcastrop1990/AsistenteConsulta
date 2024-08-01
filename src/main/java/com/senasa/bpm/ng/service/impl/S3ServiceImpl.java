package com.senasa.bpm.ng.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.senasa.bpm.ng.dao.CitaDao;
import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaPaciente;
import com.senasa.bpm.ng.service.CitaService;
import com.senasa.bpm.ng.service.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {
    @Autowired
    private AmazonS3 s3Client;

    private final String bucketName = "cirugia24horas-bucket-imagenes";

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
