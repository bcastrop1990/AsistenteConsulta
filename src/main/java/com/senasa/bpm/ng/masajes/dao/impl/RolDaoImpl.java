
package com.senasa.bpm.ng.masajes.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.masajes.dao.RolDao;
import com.senasa.bpm.ng.masajes.model.Acceso;
import com.senasa.bpm.ng.masajes.model.Rol;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
        String sql = "SELECT r.id, r.nombre, r.empresa_id, \n" +
                "GROUP_CONCAT(a.nombre SEPARATOR ', ') AS accesos\n" +
                "FROM rol r\n" +
                "INNER JOIN rol_acceso ra ON r.id = ra.rol\n" +
                "INNER JOIN acceso a ON a.id = ra.acceso\n" +
                "WHERE r.empresa_id = ?\n" +
                "GROUP BY r.id, r.nombre, r.empresa_id;";
        return jdbcTemplate.query(sql, new Object[]{empresa_id}, new RolRowMapper());
    }

    private static class RolRowMapper implements RowMapper<Rol> {
        @Override
        public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
            String accesosList = rs.getString("accesos");
            List<Acceso> accesos = Arrays.stream(accesosList.split(","))
                    .map(String::trim)
                    .map(Acceso::new)
                    .collect(Collectors.toList());
            return Rol.builder()
                    .id(rs.getLong("id"))
                    .empresa_id(rs.getLong("empresa_id"))
                    .nombre(rs.getString("nombre"))
                    .acceso(accesos)
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

