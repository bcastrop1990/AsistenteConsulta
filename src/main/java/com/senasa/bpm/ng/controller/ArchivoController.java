package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.response.*;
import com.senasa.bpm.ng.service.DataService;
import com.senasa.bpm.ng.service.S3Service;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
@CrossOrigin(origins = "*")
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
