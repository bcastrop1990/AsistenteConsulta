package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.model.response.EnfermedadResponse;
import com.senasa.bpm.ng.model.response.EnfermedadesPrimariasResponse;
import com.senasa.bpm.ng.service.EnfermedadService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enfermedad")
@AllArgsConstructor
public class EnfermedadController {
    @Autowired
    private EnfermedadService enfermedadService;

    @GetMapping("/listarEnfermedadesPrimarias")
    public ResponseEntity<ApiResponse<List<EnfermedadesPrimariasResponse>>> listarEnfermedadesPrimarias() {
        return ResponseEntity.ok(
                ApiResponse.<List<EnfermedadesPrimariasResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarEnfermedadesPrimarias())
                        .build());
    }
    @GetMapping("/listarEnfermedadesPorEnfPr/{id_EnfPr}")
    public ResponseEntity<ApiResponse<List<EnfermedadResponse>>> listarPorEnfrPr(@PathVariable Long id_EnfPr) {
        return ResponseEntity.ok(
                ApiResponse.<List<EnfermedadResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarPorEnfrPr(id_EnfPr))
                        .build());
    }

}
