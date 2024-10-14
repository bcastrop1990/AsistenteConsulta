package com.senasa.bpm.ng.masajes.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorBean {
  private Long id_especialidad;
  private String nombre_doctor;
  private String imagen_url;
  private String email;
}
