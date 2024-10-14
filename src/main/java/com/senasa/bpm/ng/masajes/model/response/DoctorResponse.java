package com.senasa.bpm.ng.masajes.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private String colorIdentificador;
    private Long idEspecialidad;
    private String imagen;
    private int estado;

}
