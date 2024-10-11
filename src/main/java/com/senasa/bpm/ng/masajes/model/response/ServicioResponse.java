package com.senasa.bpm.ng.masajes.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioResponse {
    private Long id_especialidad;
    private Long id_servicios;
    private String nombre;
    private String imagen_url;
    private String costo;
}
