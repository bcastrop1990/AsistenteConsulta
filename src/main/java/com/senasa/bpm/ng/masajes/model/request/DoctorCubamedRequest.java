package com.senasa.bpm.ng.masajes.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorCubamedRequest {
  private String nombre;
  private Long idEspecialidad;
}
