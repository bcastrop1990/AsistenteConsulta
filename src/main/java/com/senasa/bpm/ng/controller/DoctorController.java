package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.EspecialidadBean;
import com.senasa.bpm.ng.model.request.DoctorCubamedRequest;
import com.senasa.bpm.ng.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedResponse;
import com.senasa.bpm.ng.service.DoctorService;
import com.senasa.bpm.ng.service.EspecialidadService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/listarDoctor")
    public ResponseEntity<ApiResponse<List<DoctorCubaMedResponse>>> listarDoctor(@RequestBody DoctorCubamedRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<List<DoctorCubaMedResponse>>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(doctorService.listarDoctor(request.getIdEspecialidad(), request.getNombre()))
                        .build());
    }

    @PostMapping("/guardar/disponibilidad")
    public ResponseEntity<ApiResponse<Void>> configurarDisponibilidadDoctor(@RequestBody DoctorDisponibilidadRequest request) {
        // Imprimir los valores recibidos
        printDoctorDisponibilidadRequest(request);

        // Llamar al servicio para procesar la solicitud
        doctorService.configurarDisponibilidadDoctor(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .build());
    }


    @GetMapping("/obtener/disponibilidad/{email}")
    public ResponseEntity<ApiResponse<DoctorCubaMedDisponibilidadResponse>> obtenerDisponibilidad(@PathVariable String email) {
        DoctorCubaMedDisponibilidadResponse disponibilidad = doctorService.obtenerDisponibilidadPorCorreo(email);

        ApiResponse<DoctorCubaMedDisponibilidadResponse> response = new ApiResponse<>(
                "0",
                "Disponibilidad del doctor obtenida con éxito",
                disponibilidad
        );

        return ResponseEntity.ok(response);
    }



    private void printDoctorDisponibilidadRequest(DoctorDisponibilidadRequest request) {
        System.out.println("ID Doctor: " + request.getIdDoctor());
        System.out.println("Fecha Inicio: " + request.getFechaInicio());
        System.out.println("Fecha Fin: " + request.getFechaFin());
        System.out.println("Días de la Semana: " + request.getDiasSemana());
        System.out.println("Color: " + request.getColor());
        System.out.println("Hora Inicio: " + request.getHoraInicio());
        System.out.println("Hora Fin: " + request.getHoraFin());
    }

}



