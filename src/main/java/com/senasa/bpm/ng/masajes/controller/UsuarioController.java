package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.Usuario;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.S3Service;
import com.senasa.bpm.ng.masajes.service.UsuarioService;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("masajes/usuario")
@AllArgsConstructor
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private S3Service s3Service;

    @PostMapping("crear")
    public ResponseEntity<ApiResponse<String>> crearUsuario(@RequestBody Usuario request) {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(usuarioService.crearUsuario(request))
                        .build());
    }




}
