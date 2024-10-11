package com.senasa.bpm.ng.masajes.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionBean {
  private String email;
  private String password;
  private String nombres;
  private String ape_Paterno;
  private String ape_Materno;
  private String dni;
  private String celular;
  private int rol;
  private String direccion;
  private String departamento;
  private String provincia;
  private String distrito;
  private String sexo;
  private String fecha_nac;
}
