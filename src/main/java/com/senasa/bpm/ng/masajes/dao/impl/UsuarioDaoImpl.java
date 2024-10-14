
package com.senasa.bpm.ng.masajes.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.masajes.dao.UsuarioDao;
import com.senasa.bpm.ng.masajes.exception.ApiValidateException;
import com.senasa.bpm.ng.masajes.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class UsuarioDaoImpl implements UsuarioDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String crearUsuario(Usuario request) {
        String email = request.getEmail();
        String sqlCount = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, email) >= 1){
            throw new ApiValidateException("El correo "+email+" ya se encuentra registrado.");
        }
        String sql = "INSERT INTO usuario (email, password, nombres, apellidos, rol, empresa_id) VALUES (?, ?, ?, ?, ?, ?)";
        return String.valueOf(jdbcTemplate.update(sql, email, request.getPassword(), request.getNombres(), request.getApellidos(), request.getRol(), request.getEmpresa_id()));
    }

}

