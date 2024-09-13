package com.senasa.bpm.ng.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRolAcceso {
  private Long empresaId;
  private Long rolId;
  private String rolNombre;
  private String accesos;
  private Long numeroUsuarios;

  // Getters y Setters
}
