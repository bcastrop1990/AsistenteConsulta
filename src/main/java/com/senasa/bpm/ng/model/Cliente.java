package com.senasa.bpm.ng.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
  private Long id;
  private String nombre;
  private String celular;
  private String categoria;
}

