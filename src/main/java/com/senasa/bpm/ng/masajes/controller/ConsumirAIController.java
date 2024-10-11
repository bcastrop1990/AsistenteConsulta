package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.Producto;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.EnfermedadService;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("masajes/bd")
@AllArgsConstructor
public class ConsumirAIController {
    @Autowired
    private EnfermedadService enfermedadService;


    @PostMapping("/guardar/{celular}")
    public ResponseEntity<ApiResponse<List<Producto>>> guardar(@PathVariable String celular){
        enfermedadService.guardarPrimero(celular);
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }
    @GetMapping("/guardarInfo/{celular}/{nombres}/{apellidos}/{estado}")
    public ResponseEntity<ApiResponse<List<Producto>>> guardarInfo(
            @PathVariable String celular,
            @PathVariable String nombres,
            @PathVariable String apellidos,
            @PathVariable String estado) {
        enfermedadService.guardar(celular, nombres, apellidos, estado);
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }
    @GetMapping("/guardarInfoMotoFacil/{celular}/{nombre_completo}/{tipo_compra}/{cuota_inicial}/{modelo}/{marca}/{ubicacion}/{email}")
    public ResponseEntity<ApiResponse<List<Producto>>> guardarInfoMotoFacil(
            @PathVariable String celular,
            @PathVariable String nombre_completo,
            @PathVariable String ubicacion,
            @PathVariable String tipo_compra,
            @PathVariable String cuota_inicial,
            @PathVariable String modelo,
            @PathVariable String email,
            @PathVariable String marca) {
        enfermedadService.guardarInfoMotoFacil(celular, nombre_completo, ubicacion, tipo_compra, cuota_inicial, modelo, marca, email);
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }
    @PostMapping("/guardarInfo/{celular}/{nombres}/{apellidos}/{estado}")
    public ResponseEntity<ApiResponse<List<Producto>>> guardarInfo2(
            @PathVariable String celular,
            @PathVariable String nombres,
            @PathVariable String apellidos,
            @PathVariable String estado) {
        enfermedadService.guardar(celular, nombres, apellidos, estado);
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }
    @GetMapping("/obtenerInfo")
    public ResponseEntity<ApiResponse<List<Producto>>> GetMapping(){
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }

}
