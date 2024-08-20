package com.senasa.bpm.ng.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
  private Long id;
  private String nombre_completo;
  private String estado;
  private String dni;
  private String tipo_compra;
  private String cuota_inicial;
  private String modelo;
  private String marca;
  private String celular;
  private String ubicacion;
  private String activo;
  private Date fecha;
}

