package com.senasa.bpm.ng.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleServicioResponse {
    private String hospitalizacion;
    private String duracion;
    private String anestesia;
    private String recuperacion_total;
    private String tipo;
    private String codigo_tipo;
    private String sedes;
    private String nombre_cx;
}
