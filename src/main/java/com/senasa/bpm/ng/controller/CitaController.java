package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaPaciente;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.CitaService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/listarCitasEmailDoctor/{emailDoctor}")
    public ResponseEntity<ApiResponse<List<Cita>>> listarCitasEmailDoctor(@PathVariable String emailDoctor) {
        return ResponseEntity.ok(
                ApiResponse.<List<Cita>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(citaService.obtenerCitasPorDoctor(emailDoctor))
                        .build());
    }
    @GetMapping("/hola")
    public ResponseEntity<ApiResponse<String>> hola() {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data("Hola mundo")
                        .build());
    }

    @PostMapping("/agendar")
    public ResponseEntity<ApiResponse<Void>> agendarCita(@RequestBody Cita cita) {
        citaService.agendarCita(cita);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }


    @GetMapping("/listarCitasEmailPaciente/{emailPaciente}")
    public ResponseEntity<ApiResponse<List<CitaPaciente>>> listarCitasEmailPaciente(@PathVariable String emailPaciente) {
        return ResponseEntity.ok(
                ApiResponse.<List<CitaPaciente>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(citaService.obtenerCitasPorPaciente(emailPaciente))
                        .build());
    }

}


