package com.senasa.bpm.ng.masajes.controller;


import com.senasa.bpm.ng.masajes.model.request.DoctorCubamedRequest;
import com.senasa.bpm.ng.masajes.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.masajes.model.request.DoctorRequest;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorCubaMedResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorResponse;
import com.senasa.bpm.ng.masajes.service.DoctorService;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masajes/doctor")
@AllArgsConstructor
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/listarDoctor")
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
                "000",
                "Disponibilidad del doctor obtenida con éxito",
                disponibilidad
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/guardar")
    public ResponseEntity<ApiResponse<DoctorResponse>> guardarDoctor(@RequestBody DoctorRequest doctorRequest) {
        DoctorResponse doctorGuardado = doctorService.guardarDoctor(doctorRequest);

        ApiResponse<DoctorResponse> response = new ApiResponse<>(
                "000",
                "Doctor guardado con éxito",
                doctorGuardado
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> editarDoctor(@PathVariable Long id, @RequestBody DoctorRequest doctorRequest) {
        DoctorResponse doctorEditado = doctorService.editarDoctor(id, doctorRequest);

        ApiResponse<DoctorResponse> response = new ApiResponse<>(
                "000",
                "Doctor actualizado con éxito",
                doctorEditado
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/listar")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> listarDoctores() {
        List<DoctorResponse> doctores = doctorService.listarTodosLosDoctores();

        ApiResponse<List<DoctorResponse>> response = new ApiResponse<>(
                "000",
                "Doctores listados con éxito",
                doctores
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/cambiarEstado/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> alternarEstadoDoctor(@PathVariable int id) {
          DoctorResponse doctorResponse = doctorService.alternarEstadoDoctor(id);

        ApiResponse<DoctorResponse> response = new ApiResponse<>(
                "000",
                "Se actualizo el estado exitosamente",
                doctorResponse
        );

        return ResponseEntity.ok(response);
    }
}



