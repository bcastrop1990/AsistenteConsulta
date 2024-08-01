package com.senasa.bpm.ng.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaPaciente {
  private Long idCita;
  private String emailDoctor;
  private String emailPaciente;
  private LocalDateTime fechaHora;
  private String descripcion;
  private int duracion;
  private Integer idTransaccion;
  private int id_servicio;
  private Double monto;
  private String imagen_url;
  private String nombre_servicio;
}

