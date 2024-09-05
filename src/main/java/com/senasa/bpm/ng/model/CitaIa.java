package com.senasa.bpm.ng.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaIa {
  private Integer id;
  private String emailDoctor;
  private String dni;
  private String nombre_completo;
  private LocalDateTime fechaHoraInicio;
  private int duracion;
  private String descripcion;
  private BigDecimal costo;
  private LocalDateTime fechahoraFinal;
  private String color;
  private Integer doctorId;
}

