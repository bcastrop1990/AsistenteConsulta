package com.senasa.bpm.ng.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
  private Long id;
  private Long empresa_id;
  private String nombres;
  private String apellidos;
  private String email;
  private String password;
  private String rol;
}

