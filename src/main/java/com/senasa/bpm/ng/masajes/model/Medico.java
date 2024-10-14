package com.senasa.bpm.ng.masajes.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medico {
  private String nombres;
  private String apellidos;
  private String codigo;
  private String imagen;
  private String especialidad;
}

