package com.senasa.bpm.ng.masajes.model.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReponse {
    private String empresaId;
    private String email;
    private String nombre;
    private String apellido;
    private String rolId;
    private String rol;
    private String avatar;
}