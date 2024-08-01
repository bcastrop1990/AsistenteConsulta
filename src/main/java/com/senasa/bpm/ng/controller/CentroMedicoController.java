package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.DoctorService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/centro-medico")
@AllArgsConstructor
public class CentroMedicoController {
    @Autowired
    private DoctorService doctorService;

    /*
    @PostMapping("crearDoctor")
    public ResponseEntity<ApiResponse<Void>> crearDoctor(@RequestBody ClinicaRequest request) {
        doctorService.crearDoctor(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }*/
}
