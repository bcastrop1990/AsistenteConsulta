package com.senasa.bpm.ng.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medico {
  private String nombres;
  private String apellidos;
  private String codigo;
  private String imagen;
  private String especialidad;
}

