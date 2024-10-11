package com.senasa.bpm.ng.masajes.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
  private Long id;
  private String nombre;
  private Long marca;
  private String desc;
  private String nombre_marca;
  private String codigo;
  private BigDecimal precio;
  private String imagen;
  private String stock;
}

