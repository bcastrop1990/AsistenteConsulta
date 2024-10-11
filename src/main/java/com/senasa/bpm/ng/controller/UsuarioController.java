package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.Usuario;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.S3Service;
import com.senasa.bpm.ng.service.UsuarioService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("masajesUsuarioController")
@RequestMapping("/usuario")
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
