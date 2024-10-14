package com.senasa.bpm.ng.masajes.service;

import com.senasa.bpm.ng.masajes.model.User;
import com.senasa.bpm.ng.masajes.model.Usuario;
import com.senasa.bpm.ng.masajes.model.UsuarioRolAcceso;

import java.util.List;

public interface UsuarioService {

    List<UsuarioRolAcceso> listarUsuarioRolAcceso(User usuario);
    String crearUsuario(Usuario request);
}
