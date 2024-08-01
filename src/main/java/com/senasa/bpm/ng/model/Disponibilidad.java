package com.senasa.bpm.ng.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Disponibilidad {
  private Long id;
  private String emailDoctor;
  private int diaSemana;
  private Time horaInicio;
  private Time horaFin;
}


