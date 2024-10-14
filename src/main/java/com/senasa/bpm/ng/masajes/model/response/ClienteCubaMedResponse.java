package com.senasa.bpm.ng.masajes.model.response;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteCubaMedResponse {
    private Long id;
    private String nombreCompleto;
    private String estado;
    private String dni;
    private String tipoCompra;
    private String cuotaInicial;
    private String modelo;
    private String marca;
    private String celular;
    private String ubicacion;
    private Date fecha;
    private String email;
    private boolean activo;
}
