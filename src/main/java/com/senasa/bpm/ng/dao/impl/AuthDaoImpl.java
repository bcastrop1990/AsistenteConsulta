package com.senasa.bpm.ng.dao.impl;

import com.senasa.bpm.ng.dao.AuthDao;
import com.senasa.bpm.ng.dao.rowmapper.LoginUserRowMapper;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.User;
import com.senasa.bpm.ng.model.response.LoginReponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Collections;
@AllArgsConstructor
@Service
@Slf4j
public class AuthDaoImpl implements AuthDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private void validar(String email, String password) {
        String sqlCount1 = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        if (jdbcTemplate.queryForObject(sqlCount1, Integer.class, email) == 0){
            throw new ApiValidateException("El correo "+email+" no se encuentra registrado.");
        }
        String sqlCount = "SELECT COUNT(*) FROM usuario WHERE email = ? and password = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, email, password) == 0){
            throw new ApiValidateException("Contraseña incorrecta.");
        }
    }
    @Override
    public LoginReponse loginUser(User user) {
        this.validar(user.getUsername(), user.getPassword());
        String sql = "SELECT " +
                "u.empresa_id, u.email, u.nombres, u.apellidos, u.rol AS rol_id, " +
                "r.nombre AS rol_nombre " +
                "FROM usuario u " +
                "INNER JOIN rol r ON u.rol = r.id " +
                "WHERE u.email = ? AND u.password = ?";
        try {
            LoginReponse response = jdbcTemplate.queryForObject(sql, new LoginUserRowMapper(), user.getUsername(), user.getPassword());
            return  response;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
