package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.User;
import com.senasa.bpm.ng.masajes.model.UsuarioRolAcceso;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.UsuarioService;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/masajes/empresa")
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
