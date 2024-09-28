package com.senasa.bpm.ng.model.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendarCitaRequest {

  private BigDecimal costo;

  private String descripcion;
  private String dni;
  private int duracion;

  private Integer id;
  private String emailDoctor;
  private String nombre_completo;
  private LocalDateTime fechaHoraInicio;
  private LocalDateTime fechahoraFinal;
  private String color;
  private Integer doctorId;
}

