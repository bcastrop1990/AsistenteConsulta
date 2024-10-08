package com.senasa.bpm.ng.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnfermedadesPrimariasResponse {
    private Long id_EnfPr;
    private String nombre;
    private String descripcion;
}
