package com.senasa.bpm.ng.masajes.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {
  private Long id;
  private Long empresa_id;
  private String nombre;
  private List<Acceso> acceso;
}

