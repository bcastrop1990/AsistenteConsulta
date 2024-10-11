package com.senasa.bpm.ng.masajes.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UsuarioRequest {
  private Long empresaId;
  private String email;
  private String password;
  private String nombre;
  private String apellido;
  private Long rolId;
}
