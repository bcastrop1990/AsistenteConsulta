package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.DataService;
import com.senasa.bpm.ng.masajes.service.S3Service;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("masajes/file")
@AllArgsConstructor
public class ArchivoController {
    @Autowired
    private DataService dataService;
    @Autowired
    private S3Service s3Service;

    @PostMapping("upload")
    public ResponseEntity<ApiResponse<String>> uploadImagen(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(s3Service.uploadFile(file))
                        .build());
    }

}
