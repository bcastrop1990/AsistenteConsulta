package com.senasa.bpm.ng.masajes.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {
  private Long id;
  private Double monto;
  private Long idCliente;
  private Integer cantidad;
  private Date fecha;
}
