package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.Categoria;
import com.senasa.bpm.ng.model.Cliente;
import com.senasa.bpm.ng.model.Marca;
import com.senasa.bpm.ng.model.Producto;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.EnfermedadService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social")
@AllArgsConstructor
public class SocialManagerController {
    @Autowired
    private EnfermedadService enfermedadService;

    @GetMapping("/listarProducto")
    public ResponseEntity<ApiResponse<List<Producto>>> listarProducto() {
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarProducto())
                        .build());
    }

    @GetMapping("/listarProducto/{marca}")
    public ResponseEntity<ApiResponse<List<Producto>>> listarPorEnfrPr(@PathVariable Long marca) {
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarProductoPorMarca(marca))
                        .build());
    }

    @GetMapping("/listarMarcas")
    public ResponseEntity<ApiResponse<List<Marca>>> listarMarcas() {
        return ResponseEntity.ok(
                ApiResponse.<List<Marca>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarMarcas())
                        .build());
    }

    @PostMapping("agregarProd")
    public ResponseEntity<ApiResponse<String>> agregarProd(@RequestBody Producto request) {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.agregarProd(request))
                        .build());
    }

    @PostMapping("/eliminarProducto/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarProducto(@PathVariable Long id) {
        enfermedadService.eliminarProducto(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data("El producto "+id+" fue eliminado.")
                        .build());
    }
    @GetMapping("/listarClientes")
    public ResponseEntity<ApiResponse<List<Cliente>>> listarClientes() {
        return ResponseEntity.ok(
                ApiResponse.<List<Cliente>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarClientes())
                        .build());
    }
    @PostMapping("/eliminarCliente/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarCliente(@PathVariable Long id) {
        enfermedadService.eliminarCliente(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data("El cliente "+id+" fue eliminado.")
                        .build());
    }

    @PostMapping("agregarCategoria")
    public ResponseEntity<ApiResponse<String>> agregarCategoria(@RequestBody Categoria request) {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.agregarCategoria(request))
                        .build());
    }

}
