package com.senasa.bpm.ng.masajes.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConfiguracionRequest {
  private int maxClientesActivos;
}
