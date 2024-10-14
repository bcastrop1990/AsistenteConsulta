package com.senasa.bpm.ng.masajes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Datos {
  private int cantidadAprobados;
  private int cantidadDesaprobados;
  private String marcaMasSolicitada;
  private int cantidadFinanciamiento;
  private int cantidadAlContado;
  private int cantidadTarjetaCredito;
}
