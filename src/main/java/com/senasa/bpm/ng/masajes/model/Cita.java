package com.senasa.bpm.ng.masajes.model;

import lombok.*;

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

