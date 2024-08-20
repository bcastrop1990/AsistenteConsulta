package com.senasa.bpm.ng.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DatosImportantes {
  private int totalFilas;
  private Map<String, Long> filasPorModelo;
  private Map<String, Long> filasPorMarca;
  private Map<String, Integer> aprobadosPorSemana;
  private Map<String, Integer> desaprobadosPorSemana;
}
