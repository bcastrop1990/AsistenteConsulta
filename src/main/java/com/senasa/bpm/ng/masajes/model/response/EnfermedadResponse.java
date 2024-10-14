package com.senasa.bpm.ng.masajes.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnfermedadResponse {
    private Long id_Enf;
    private Long id_EnfPr;
    private String nombre;
    private String descripcion;
}
