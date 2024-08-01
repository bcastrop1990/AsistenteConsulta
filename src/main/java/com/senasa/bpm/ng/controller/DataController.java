package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.response.*;
import com.senasa.bpm.ng.service.DataService;
import com.senasa.bpm.ng.service.EnfermedadService;
import com.senasa.bpm.ng.service.PaymentService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
public class DataController {
    @Autowired
    private DataService dataService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/listarEspecialidades")
    public ResponseEntity<ApiResponse<List<EspecialidadResponse>>> listarEspecialidades() {
        return ResponseEntity.ok(
                ApiResponse.<List<EspecialidadResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(dataService.listarEspecialidades())
                        .build());
    }

    @GetMapping("/listarServicioPorEsp/{id_especialidad}")
    public ResponseEntity<ApiResponse<List<ServicioResponse>>> listarServicioPorEsp(@PathVariable Long id_especialidad) {
        return ResponseEntity.ok(
                ApiResponse.<List<ServicioResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(dataService.listarServicioPorEsp(id_especialidad))
                        .build());
    }
    @GetMapping("/obtenerDetalleSrvc/{id_servicio}")
    public ResponseEntity<ApiResponse<List<DetalleServicioResponse>>> obtenerDetalle(@PathVariable Integer id_servicio) {
        return ResponseEntity.ok(
                ApiResponse.<List<DetalleServicioResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(dataService.obtenerDetalle(id_servicio))
                        .build());
    }

    @GetMapping("/listarDoctores")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> listarDoctores() {
        return ResponseEntity.ok(
                ApiResponse.<List<DoctorResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(dataService.listarDoctores())
                        .build());
    }
    @GetMapping("/listarDoctoresPorEsp/{id_especialidad}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> listarDoctoresPorEsp(@PathVariable Long id_especialidad) {
        return ResponseEntity.ok(
                ApiResponse.<List<DoctorResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(dataService.listarDoctoresPorEsp(id_especialidad))
                        .build());
    }
    @GetMapping("/listarDoctoresPorSrvc/{id_servicio}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> listarDoctoresPorSrvc(@PathVariable Integer id_servicio) {
        return ResponseEntity.ok(
                ApiResponse.<List<DoctorResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(dataService.listarDoctoresPorSrvc(id_servicio))
                        .build());
    }

}
