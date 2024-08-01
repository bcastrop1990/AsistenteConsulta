package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CitaRequest {
//  private String accessToken;
//  private String summary;
//  private String location;
//  private String description;
//  private String startTime; // ISO 8601 string
//  private String endTime; // ISO 8601 string
//  private String[] attendeeEmails;
  private Long id_cita;
  private String email_doctor;
  private String email_paciente;
  private LocalDateTime fechaHora;
  private String descripcion;
  private int duracion;
  private int id_transaccion;
}
