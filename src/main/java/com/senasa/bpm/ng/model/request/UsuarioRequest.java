package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UsuarioRequest {
  private String email;
  private String password;
  private String nombres;
  private String ape_Paterno;
  private String ape_Materno;
  private String codigo;
  private int rol;
}
