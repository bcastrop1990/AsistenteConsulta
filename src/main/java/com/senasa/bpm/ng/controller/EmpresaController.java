package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.S3Service;
import com.senasa.bpm.ng.service.UsuarioService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
@AllArgsConstructor
public class EmpresaController {
    @Autowired
    private UsuarioService usuarioService;

/*
    @PostMapping("crear")
    public ResponseEntity<ApiResponse<String>> crearUsuario(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(usuarioService.crearUsuario(request))
                        .build());
    }*/
}
