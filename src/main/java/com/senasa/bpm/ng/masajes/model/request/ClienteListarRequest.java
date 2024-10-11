package com.senasa.bpm.ng.masajes.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClienteListarRequest {
  private String celular;
  private String nombreCompleto;
  private String ubicacion;
  private String tipoCompra;
  private String cuotaInicial;
  private String modelo;
  private String marca;
  private String email;
  private LocalDate fechaDesde;
  private LocalDate fechaHasta;
  private String dni;
  private String estado;
  private int page;
 }
