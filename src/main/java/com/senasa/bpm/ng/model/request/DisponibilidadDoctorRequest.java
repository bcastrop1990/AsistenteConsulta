package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DisponibilidadDoctorRequest {
  private String emailDoctor;
  private int diaSemana;
  private String horaInicio;
  private String horaFin;
}
