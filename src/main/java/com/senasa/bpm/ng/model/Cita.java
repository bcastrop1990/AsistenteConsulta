package com.senasa.bpm.ng.model;

import lombok.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {
  private Long idCita;
  private String emailDoctor;
  private String emailPaciente;
  private LocalDateTime fechaHora;
  private String descripcion;
  private int duracion;
  private Integer idTransaccion;
  private int id_servicio;
  private Double monto;
}

