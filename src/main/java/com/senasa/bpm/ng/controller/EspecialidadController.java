package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.EspecialidadBean;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.EspecialidadService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
@AllArgsConstructor
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping("/listar")
    public ResponseEntity<ApiResponse<List<EspecialidadBean>>> listarEspecialidades() {
        return ResponseEntity.ok(
                ApiResponse.<List<EspecialidadBean>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(especialidadService.listarEspecialidad())
                        .build());
    }





}



