package com.senasa.bpm.ng.masajes.model.request;

import lombok.*;

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

