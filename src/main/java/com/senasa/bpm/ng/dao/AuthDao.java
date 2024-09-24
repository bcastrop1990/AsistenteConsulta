package com.senasa.bpm.ng.dao;
import com.senasa.bpm.ng.model.User;
import com.senasa.bpm.ng.model.response.LoginReponse;
public interface AuthDao {
    LoginReponse loginUser (User user);
}