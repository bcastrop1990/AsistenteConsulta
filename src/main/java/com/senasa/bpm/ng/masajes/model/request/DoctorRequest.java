package com.senasa.bpm.ng.masajes.model.request;

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
  private String celular;
  private String email;
  private String colorIdentificador;
  private Long idEspecialidad;
  private String imagen;
  private int estado;
  private String password;

}
