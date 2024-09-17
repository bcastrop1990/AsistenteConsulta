
package com.senasa.bpm.ng.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.dao.RolDao;
import com.senasa.bpm.ng.dao.UsuarioDao;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.Acceso;
import com.senasa.bpm.ng.model.Rol;
import com.senasa.bpm.ng.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@AllArgsConstructor
@Service
@Slf4j
public class RolDaoImpl implements RolDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String crear(Rol request){
        Long rol = this.crearRol(request);
        List<Acceso> accesos = request.getAcceso();
        String sql = "INSERT INTO rol_acceso (rol, acceso) VALUES (?, ?)";

        for (Acceso acceso : accesos) {
            jdbcTemplate.update(sql, rol, acceso.getId());
        }
        return "Rol creado con exito.";
    }

    public Long crearRol(Rol request) {
        String sql = "INSERT INTO rol (nombre, empresa_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.getNombre());
            ps.setLong(2, request.getEmpresa_id());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.longValue() : null;
    }

    @Override
    public List<Rol> listarRoles(Long empresa_id) {
        String sql = "SELECT id, empresa_id, nombre FROM rol WHERE empresa_id = ?";
        return jdbcTemplate.query(sql, new Object[]{empresa_id}, new RolRowMapper());
    }

    private static class RolRowMapper implements RowMapper<Rol> {
        @Override
        public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Rol.builder()
                    .id(rs.getLong("id"))
                    .empresa_id(rs.getLong("empresa_id"))
                    .nombre(rs.getString("nombre"))
                    .build();
        }
    }

    @Override
    public List<Acceso> listarAccesos() {
        String sql = "SELECT id, nombre FROM acceso";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                Acceso.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("nombre"))
                        .build()
        );
    }

}
