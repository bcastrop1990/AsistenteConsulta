package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.EspecialidadBean;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.EspecialidadService;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("masajes/especialidad")
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



