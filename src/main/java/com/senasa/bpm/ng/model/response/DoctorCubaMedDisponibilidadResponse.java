package com.senasa.bpm.ng.model.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class DoctorCubaMedDisponibilidadResponse {
    private Long idDoctor;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<String> diasSemana;
    private String color;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
