package com.senasa.bpm.ng.masajes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorDisponibilidad  {
  private int id;
  private int idDoctor;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private String diasSemana; // Representa los días de la semana como una cadena (por ejemplo, "Lunes,Martes,Miercoles")
  private LocalTime horaInicio;
  private LocalTime horaFin;
  private String color;
}


