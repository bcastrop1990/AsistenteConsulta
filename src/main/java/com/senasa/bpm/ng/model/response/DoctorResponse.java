package com.senasa.bpm.ng.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {
    private Long id_especialidad;
    private String nombre_doctor;
    private String imagen_url;
    private String email;
}
