package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.DoctorService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/medico")
@AllArgsConstructor
public class MedicoController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("validar")
    public ResponseEntity<ApiResponse<Medico>> validarDoctor(@RequestBody DoctorRequest request) throws IOException {
        return ResponseEntity.ok(
                ApiResponse.<Medico>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(doctorService.validarDoctor(request))
                        .build());
    }

    /*
    @PostMapping("crear")
    public ResponseEntity<ApiResponse<String>> crear(@RequestBody DoctorRequest request) throws IOException {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(doctorService.validarDoctor(request))
                        .build());
    }

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
