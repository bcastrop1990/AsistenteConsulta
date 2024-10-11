package com.senasa.bpm.ng.masajes.dao;
import com.senasa.bpm.ng.masajes.model.ChangePassword;
import com.senasa.bpm.ng.masajes.model.User;
import com.senasa.bpm.ng.masajes.model.response.LoginReponse;

public interface MasajesAuthDao {
    LoginReponse loginUser (User user);
    Void changePassword (ChangePassword user);
}