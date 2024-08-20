package com.senasa.bpm.ng.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaIa {
  private String emailDoctor;
  private String dni;
  private String nombre_completo;
  private LocalDateTime fechaHora;
  private int duracion;
  private String descripcion;
  private String costo;
}

