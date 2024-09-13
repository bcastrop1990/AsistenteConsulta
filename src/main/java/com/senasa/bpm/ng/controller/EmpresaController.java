package com.senasa.bpm.ng.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senasa.bpm.ng.model.UsuarioRolAcceso;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/empresa")
@AllArgsConstructor
public class EmpresaController {
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("obtenerInfo")
    public ResponseEntity<ApiResponse<List<UsuarioRolAcceso>>> obtenerInfo(@RequestBody String requestBody) {
        String email = extractEmailFromRequest(requestBody);

        System.out.println("Email extraído: " + email);

        return ResponseEntity.ok(
                ApiResponse.<List<UsuarioRolAcceso>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(usuarioService.listarUsuarioRolAcceso(email))
                        .build());
    }

    private String extractEmailFromRequest(String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            return jsonNode.get("usuario").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
