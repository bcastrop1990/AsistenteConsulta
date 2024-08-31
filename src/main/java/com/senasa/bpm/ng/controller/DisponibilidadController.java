package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.request.DisponibilidadDoctorRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.DisponibilidadService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/disponibilidad")
@CrossOrigin(origins = "*")
public class DisponibilidadController {
    @Autowired
    private DisponibilidadService disponibilidadService;

    @PostMapping("/definirDisponibilidad")
    public ResponseEntity<ApiResponse<Void>> listarServicioPorEsp(@RequestBody DisponibilidadDoctorRequest disponibilidad) {
        disponibilidadService.definirDisponibilidad(disponibilidad);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }
    @GetMapping("/horarios-disponibles/{emailDoctor}/{fecha}")
    public ResponseEntity<ApiResponse<List<LocalDateTime>>> listarServicioPorEsp(@PathVariable String emailDoctor, @PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return ResponseEntity.ok(
                ApiResponse.<List<LocalDateTime>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(disponibilidadService.obtenerHorariosDisponibles(emailDoctor, localDate))
                        .build());
    }

}
