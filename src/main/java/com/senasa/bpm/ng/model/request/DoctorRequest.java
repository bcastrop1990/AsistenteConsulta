package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorRequest {
  private String nombre;
  private String apellido;
  private String codigo;
  private String colegio;
  private Long idEspecialidad;
}
