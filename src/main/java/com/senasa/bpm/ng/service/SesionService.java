package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.UserResponse;

import javax.mail.MessagingException;
import java.util.List;

public interface SesionService {

    String crearUsuario(UsuarioRequest request);
    List<UserResponse> iniciar(String email, String password);
//    String loginGoogle(String email, String accessToken);
    String obtenerRefreshToken(String email);
    String enviarCodigo(String email) throws MessagingException;
    void crearClinica(ClinicaRequest request);
    //void crearDoctor(ClinicaRequest request);

}
