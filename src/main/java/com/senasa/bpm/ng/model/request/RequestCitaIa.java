package com.senasa.bpm.ng.model.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCitaIa {
  private String emailDoctor;
  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFinal;
}

