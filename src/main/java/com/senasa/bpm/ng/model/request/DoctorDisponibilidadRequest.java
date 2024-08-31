package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorDisponibilidadRequest {
  private Long idDoctor;
  private String fechaInicio;
  private String fechaFin;
  private List<String> diasSemana;
  private String color;
  private String email;
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
