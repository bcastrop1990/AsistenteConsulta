package com.senasa.bpm.ng.masajes.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String nombres;
    private String ape_Paterno;
    private String ape_Materno;
    private String rol;
}
