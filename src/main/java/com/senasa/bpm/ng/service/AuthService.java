package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.User;
import com.senasa.bpm.ng.model.UsuarioRolAcceso;
import com.senasa.bpm.ng.model.response.LoginReponse;

import java.util.List;

public interface AuthService {

    LoginReponse loginUser(User user);

}