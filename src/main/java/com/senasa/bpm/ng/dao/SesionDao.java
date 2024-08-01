package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.bean.UserBean;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.UserResponse;

import java.util.List;

public interface SesionDao {
    String crearUsuario(UsuarioRequest request);
    List<UserBean> iniciar(String email, String password);
//    String loginGoogle(String email, String accessToken);
    String obtenerRefreshToken(String email);
    //void enviarCodigo(String to, String subject,String text);
    int crearClinica(ClinicaRequest request);
    void relacionarImagenesConClinica(String url, int id);
    String guardarCodigoVerificacion(String url, String codigo);
    boolean validarCodigo(String email, String codigo);
}
