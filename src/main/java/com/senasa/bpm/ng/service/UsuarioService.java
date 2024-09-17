package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.User;
import com.senasa.bpm.ng.model.Usuario;
import com.senasa.bpm.ng.model.UsuarioRolAcceso;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.UserResponse;

import javax.mail.MessagingException;
import java.util.List;

public interface UsuarioService {

    List<UsuarioRolAcceso> listarUsuarioRolAcceso(User usuario);
    String crearUsuario(Usuario request);
}
