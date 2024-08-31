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
    private LocalTime horaInicioLunes;
    private LocalTime horaFinLunes;
    private LocalTime horaInicioMartes;
    private LocalTime horaFinMartes;
    private LocalTime horaInicioMiercoles;
    private LocalTime horaFinMiercoles;
    private LocalTime horaInicioJueves;
    private LocalTime horaFinJueves;
    private LocalTime horaInicioViernes;
    private LocalTime horaFinViernes;
    private LocalTime horaInicioSabado;
    private LocalTime horaFinSabado;
    private LocalTime horaInicioDomingo;
    private LocalTime horaFinDomingo;
}
