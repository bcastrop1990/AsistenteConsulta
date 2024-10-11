package com.senasa.bpm.ng.masajes.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadResponse {
    private Long id_especialidad;
    private String nombre;
    private String imagen_url;
}
