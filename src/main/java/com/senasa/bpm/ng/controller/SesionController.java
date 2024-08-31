package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.model.response.UserResponse;
import com.senasa.bpm.ng.service.S3Service;
import com.senasa.bpm.ng.service.SesionService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/sesion")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class SesionController {
    @Autowired
    private SesionService sesionService;
    @Autowired
    private S3Service s3Service;

    @PostMapping("crearUsuario")
    public ResponseEntity<ApiResponse<String>> crearUsuario(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(sesionService.crearUsuario(request))
                        .build());
    }

    @PostMapping("iniciar/{email}/{password}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> iniciar(@PathVariable String email, @PathVariable String password) {
        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(sesionService.iniciar(email, password))
                        .build());
    }

    @GetMapping("obtenerRefreshToken/{email}")
    public ResponseEntity<ApiResponse<String>> obtenerRefreshToken(@PathVariable String email) {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(sesionService.obtenerRefreshToken(email))
                        .build());
    }

    @PostMapping("/enviarCodigo/{email}")
    public ResponseEntity<ApiResponse<String>> sendCode(@PathVariable String email) throws MessagingException {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(sesionService.enviarCodigo(email))
                        .build());
    }

    @PostMapping("crearClinica")
    public ResponseEntity<ApiResponse<Void>> crearClinica(@RequestBody ClinicaRequest request) {
        sesionService.crearClinica(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }

}
