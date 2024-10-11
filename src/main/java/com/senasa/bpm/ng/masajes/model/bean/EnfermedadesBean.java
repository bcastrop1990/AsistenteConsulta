package com.senasa.bpm.ng.masajes.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnfermedadesBean {
  private Long id_EnfPr;
  private Long id_Enf;
  private String nombre;
  private String descripcion;
}
