package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
  private String horaInicio;
  private String horaFin;
  private String email;
}
