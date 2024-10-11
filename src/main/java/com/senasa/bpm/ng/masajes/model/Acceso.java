package com.senasa.bpm.ng.masajes.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Acceso {
  private Long id;
  private String nombre;

  public Acceso(String nombre) {
    this.nombre = nombre;
  }
}

