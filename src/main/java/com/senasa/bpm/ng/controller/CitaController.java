package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.request.AgendarCitaRequest;
import com.senasa.bpm.ng.model.request.RequestCitaIa;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.CitaService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/citas")
@AllArgsConstructor
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping("/agendar")
    public ResponseEntity<ApiResponse<Void>> agendarCita(@RequestBody AgendarCitaRequest cita) {
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
    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<LocalDateTime>>> obtenerHorariosDisponibles(
            @RequestParam Long doctorId,
            @RequestParam String fecha) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";

        try {
            // Mostrar doctorId y fecha en verde
            System.out.println(ANSI_GREEN + "DoctorId recibido: " + doctorId + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Fecha recibida: " + fecha + ANSI_RESET);

            // Convertir la fecha desde el formato String al tipo LocalDate
            LocalDate fechaLocalDate = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE);

            // Llamar al servicio para obtener los horarios disponibles
            List<LocalDateTime> horariosDisponibles = citaService.obtenerHorariosDisponibles(doctorId, fechaLocalDate);

            // Mostrar horarios obtenidos en azul
            System.out.println(ANSI_BLUE + "Horarios Disponibles: " + horariosDisponibles + ANSI_RESET);

            // Retornar la respuesta en formato ApiResponse
            ApiResponse<List<LocalDateTime>> response = new ApiResponse<>("000", "Horarios disponibles obtenidos exitosamente", horariosDisponibles);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Mostrar el error en rojo
            System.out.println(ANSI_RED + "Error capturado: " + e.getMessage() + ANSI_RESET);
            e.printStackTrace();  // También imprime el stack trace del error en la terminal

            ApiResponse<List<LocalDateTime>> response = new ApiResponse<>("1", "Error al obtener los horarios disponibles", null);
            return ResponseEntity.status(500).body(response);

        }

    }
}



