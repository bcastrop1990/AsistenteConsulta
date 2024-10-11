package com.senasa.bpm.ng.masajes.service.impl;
import com.senasa.bpm.ng.masajes.dao.AuthDao;
import com.senasa.bpm.ng.masajes.model.ChangePassword;
import com.senasa.bpm.ng.masajes.model.User;
import com.senasa.bpm.ng.masajes.model.response.LoginReponse;
import com.senasa.bpm.ng.masajes.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Void changePassword(ChangePassword user) {
        return authDao.changePassword(user);
    }
}