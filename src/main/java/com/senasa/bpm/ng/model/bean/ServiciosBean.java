package com.senasa.bpm.ng.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiciosBean {
  private Long id_especialidad;
  private Long id_servicio;
  private String nombre;
  private String imagen_url;
  private String costo;
  private String hospitalizacion;
  private String duracion;
  private String anestesia;
  private String recuperacion_total;
  private String tipo;
  private String codigo_tipo;
  private String sedes;
  private String nombre_cx;
}
