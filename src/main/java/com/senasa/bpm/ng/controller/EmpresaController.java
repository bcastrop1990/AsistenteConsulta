package com.senasa.bpm.ng.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senasa.bpm.ng.model.User;
import com.senasa.bpm.ng.model.UsuarioRolAcceso;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.S3Service;
import com.senasa.bpm.ng.service.UsuarioService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/empresa")
@AllArgsConstructor
public class EmpresaController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("obtenerInfo")
    public ResponseEntity<ApiResponse<List<UsuarioRolAcceso>>> obtenerInfo(@RequestBody User user) {
        return ResponseEntity.ok(
                ApiResponse.<List<UsuarioRolAcceso>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(usuarioService.listarUsuarioRolAcceso(user))
                        .build());
    }

}
