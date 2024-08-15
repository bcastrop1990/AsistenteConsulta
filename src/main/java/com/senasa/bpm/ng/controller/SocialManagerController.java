package com.senasa.bpm.ng.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.senasa.bpm.ng.model.*;
import com.senasa.bpm.ng.model.request.ClienteListarRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.EnfermedadService;
import com.senasa.bpm.ng.service.SocialManagerService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/social")
@AllArgsConstructor
public class SocialManagerController {
    @Autowired
    private EnfermedadService enfermedadService;

    @Autowired
    private SocialManagerService socialManagerService;


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
    @PostMapping("/listarClientesMotoFacil")
    public ResponseEntity<ApiResponse<Page<Cliente>>> listarClientesMotoFacil(@RequestBody ClienteListarRequest request) {
        Page<Cliente> clientes = enfermedadService.listarClientesMotoFacil(request);
        return ResponseEntity.ok(
                ApiResponse.<Page<Cliente>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(clientes)
                        .build());
    }


    @GetMapping("/listarEstadosFinanciamiento")
    public ResponseEntity<ApiResponse<List<EstadoFinanciamiento>>> listarEstadosFinanciamientoMotoFacil() {
        List<EstadoFinanciamiento> estados = socialManagerService.listarEstadosFinanciamiento();
        return ResponseEntity.ok(
                ApiResponse.<List<EstadoFinanciamiento>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(estados)
                        .build());
    }

    @GetMapping("/listarTipoCompra")
    public ResponseEntity<ApiResponse<List<TipoDeCompra>>> listarTiposDeCompraMotoFacil() {
        List<TipoDeCompra> tipos = socialManagerService.listarTipoCompra();
        return ResponseEntity.ok(
                ApiResponse.<List<TipoDeCompra>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(tipos)
                        .build());
    }

    @GetMapping("/listarMarcaMotoFacil")
    public ResponseEntity<ApiResponse<List<MarcaMotoFacil>>> listarMarcaMotoFacil() {
        List<MarcaMotoFacil> tipos = socialManagerService.listarMarcaMotoFacil();
        return ResponseEntity.ok(
                ApiResponse.<List<MarcaMotoFacil>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(tipos)
                        .build());
    }


    @GetMapping("/listarModeloMotoFacil")
    public ResponseEntity<ApiResponse<List<ModeloMotoFacil>>> listarModeloMotoFacil() {
        List<ModeloMotoFacil> tipos = socialManagerService.listarModeloMotoFacil();
        return ResponseEntity.ok(
                ApiResponse.<List<ModeloMotoFacil>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(tipos)
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
    @PostMapping("/eliminarCategoria/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarCategoria(@PathVariable Long id) {
        enfermedadService.eliminarCategoria(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data("La categoria con el id "+id+" fue eliminado.")
                        .build());
    }
    @GetMapping("/listarPorFecha")
    public ResponseEntity<ApiResponse<List<Venta>>> listarPorFecha(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date desde,
                                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date hasta) {
        return ResponseEntity.ok(
                ApiResponse.<List<Venta>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarPorFecha(desde, hasta))
                        .build());
    }

    @GetMapping("/listarTodo")
    public ResponseEntity<ApiResponse<List<Venta>>> listarTodo() {
        return ResponseEntity.ok(
                ApiResponse.<List<Venta>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarTodo())
                        .build());
    }

    @GetMapping("/listarPorMes")
    public ResponseEntity<ApiResponse<List<Venta>>> listarPorMes(@RequestParam int mes, @RequestParam int año) {
        return ResponseEntity.ok(
                ApiResponse.<List<Venta>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarPorMes(mes, año))
                        .build());
    }

    @GetMapping("/listarPorAño")
    public ResponseEntity<ApiResponse<List<Venta>>> listarPorAño(@RequestParam int año) {
        return ResponseEntity.ok(
                ApiResponse.<List<Venta>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarPorAño(año))
                        .build());
    }

    @GetMapping("/listarPorDia")
    public ResponseEntity<ApiResponse<List<Venta>>> listarPorDia(@RequestParam int dia, @RequestParam int mes, @RequestParam int año) {
        return ResponseEntity.ok(
                ApiResponse.<List<Venta>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(enfermedadService.listarPorDia(dia, mes, año))
                        .build());
    }


}
