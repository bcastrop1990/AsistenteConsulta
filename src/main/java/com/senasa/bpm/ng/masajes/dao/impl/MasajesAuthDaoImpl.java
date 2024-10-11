package com.senasa.bpm.ng.masajes.dao.impl;

import com.senasa.bpm.ng.masajes.dao.MasajesAuthDao;
import com.senasa.bpm.ng.masajes.dao.rowmapper.LoginUserRowMapper;
import com.senasa.bpm.ng.masajes.exception.ApiValidateException;
import com.senasa.bpm.ng.masajes.model.ChangePassword;
import com.senasa.bpm.ng.masajes.model.User;
import com.senasa.bpm.ng.masajes.model.response.LoginReponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class MasajesAuthDaoImpl implements MasajesAuthDao {
    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;
    private void validar(String email, String password) {
        String sqlCount1 = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        if (secondaryJdbcTemplate.queryForObject(sqlCount1, Integer.class, email) == 0){
            throw new ApiValidateException("El correo "+email+" no se encuentra registrado.");
        }
        String sqlCount = "SELECT COUNT(*) FROM usuario WHERE email = ? and password = ?";
        if (secondaryJdbcTemplate.queryForObject(sqlCount, Integer.class, email, password) == 0){
            throw new ApiValidateException("Contraseña incorrecta.");
        }
    }
    @Override
    public LoginReponse loginUser(User user) {
        this.validar(user.getUsername(), user.getPassword());
        String sql = "SELECT " +
                "u.empresa_id, u.email, u.nombres, u.apellidos, u.rol AS rol_id, " +
                "r.nombre AS rol_nombre, " +
                "d.imagen AS doctor_imagen " +
                "FROM usuario u " +
                "INNER JOIN rol r ON u.rol = r.id " +
                "INNER JOIN doctores d ON d.usuario_id = u.id " +
                "WHERE u.email = ? AND u.password = ?";
        try {
            LoginReponse response = secondaryJdbcTemplate.queryForObject(sql, new LoginUserRowMapper(), user.getUsername(), user.getPassword());
            return  response;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Void changePassword(ChangePassword user) {
        String sqlCount = "SELECT COUNT(*) FROM usuario WHERE email = ? and password = ?";
        if (secondaryJdbcTemplate.queryForObject(sqlCount, Integer.class, user.getUsername(), user.getPassword()) == 0){
            throw new ApiValidateException("Contraseña incorrecta.");
        }
        String sql = "UPDATE usuario SET password = ? WHERE email = ?";
        secondaryJdbcTemplate.update(sql, user.getNewPassword(), user.getUsername());
        return null;
    }
}
