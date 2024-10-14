package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.Acceso;
import com.senasa.bpm.ng.masajes.model.Rol;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.RolService;
import com.senasa.bpm.ng.masajes.service.S3Service;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masajes/rol")
@AllArgsConstructor
public class RolController {
    @Autowired
    private RolService rolService;
    @Autowired
    private S3Service s3Service;

    @PostMapping("crear")
    public ResponseEntity<ApiResponse<String>> crear(@RequestBody Rol request) {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(rolService.crear(request))
                        .build());
    }
    @GetMapping("/listarRoles/{empresa_id}")
    public ResponseEntity<ApiResponse<List<Rol>>> listarServicioPorEsp(@PathVariable Long empresa_id) {
        return ResponseEntity.ok(
                ApiResponse.<List<Rol>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(rolService.listarRoles(empresa_id))
                        .build());
    }
    @GetMapping("/listarAccesos")
        public ResponseEntity<ApiResponse<List<Acceso>>> listarAccesos() {
        return ResponseEntity.ok(
                ApiResponse.<List<Acceso>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(rolService.listarAccesos())
                        .build());
    }




}
