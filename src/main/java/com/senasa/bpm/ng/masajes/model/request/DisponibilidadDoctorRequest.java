package com.senasa.bpm.ng.masajes.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
