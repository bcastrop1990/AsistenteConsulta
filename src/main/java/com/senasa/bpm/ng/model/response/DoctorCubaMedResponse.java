package com.senasa.bpm.ng.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorCubaMedResponse {
    private Long id_especialidad;
    private Long idDoctor;
    private String email;
    private String nombres;
    private String apellidos;
    private String imagen;
    private String numeroCelular;
    private String colorIndentificador;
 }
