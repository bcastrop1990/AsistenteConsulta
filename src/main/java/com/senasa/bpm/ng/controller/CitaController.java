package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.request.RequestCitaIa;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.ApiService;
import com.senasa.bpm.ng.service.CitaService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping("/agendar")
    public ResponseEntity<ApiResponse<Void>> agendarCita(@RequestBody CitaIa cita) {
        citaService.agendarCita(cita);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }



    @PostMapping("/listarTodasCitas")
    public ResponseEntity<ApiResponse<List<CitaIa>>> listarTodasCitasRangoFecha(@RequestBody RequestCitaIa request) {
        return ResponseEntity.ok(
                ApiResponse.<List<CitaIa>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(citaService.listarTodasCitasRangoFecha(request))
                        .build());
    }

}



