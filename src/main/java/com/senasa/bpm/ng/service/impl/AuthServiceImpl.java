package com.senasa.bpm.ng.service.impl;
import com.senasa.bpm.ng.dao.AuthDao;
import com.senasa.bpm.ng.model.User;
import com.senasa.bpm.ng.model.UsuarioRolAcceso;
import com.senasa.bpm.ng.model.response.LoginReponse;
import com.senasa.bpm.ng.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl  implements AuthService {
    @Autowired
    private AuthDao authDao;
    @Override
    public LoginReponse loginUser(User user) {
        return authDao.loginUser(user);
    }
}