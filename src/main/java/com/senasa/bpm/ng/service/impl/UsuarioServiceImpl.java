package com.senasa.bpm.ng.service.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.senasa.bpm.ng.dao.EspecialidadDao;
import com.senasa.bpm.ng.dao.UsuarioDao;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.User;
import com.senasa.bpm.ng.model.Usuario;
import com.senasa.bpm.ng.model.UsuarioRolAcceso;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private final AmazonSimpleEmailService sesClient;
    @Autowired
    private UsuarioDao usuarioDao;

    public String crearUsuario(Usuario request) {

        return usuarioDao.crearUsuario(request);
    }

    @PersistenceContext
    private EntityManager entityManager;

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

    public List<UsuarioRolAcceso> listarUsuarioRolAcceso(User user) {
        this.validar(user.getUsername(), user.getPassword());

        String sql = "SELECT " +
                "u.empresa_id, " +
                "r.id AS rol_id, " +
                "r.nombre AS rol_nombre, u.nombres, u.apellidos, u.email, " +
                "GROUP_CONCAT(DISTINCT a.nombre ORDER BY a.nombre ASC SEPARATOR ', ') AS accesos, " +
                "COUNT(DISTINCT u.id) AS numero_usuarios " +
                "FROM usuario u " +
                "INNER JOIN rol r ON u.rol = r.id " +
                "INNER JOIN rol_acceso ra ON r.id = ra.rol " +
                "INNER JOIN acceso a ON ra.acceso = a.id " +
                "WHERE u.email = ? " +
                "GROUP BY u.empresa_id, r.id, r.nombre " +
                "ORDER BY u.empresa_id, r.id";

        System.out.println("Ejecutando consulta SQL: " + sql);
        System.out.println("Parámetro de correo electrónico: " + user.getUsername());

        try {
            List<UsuarioRolAcceso> resultados = jdbcTemplate.query(sql, new UsuarioRolAccesoRowMapper(), user.getUsername());
            return resultados;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron resultados para el correo electrónico: " + user.getUsername());
            return Collections.emptyList();
        }
    }

    private static class UsuarioRolAccesoRowMapper implements RowMapper<UsuarioRolAcceso> {

        @Override
        public UsuarioRolAcceso mapRow(ResultSet rs, int rowNum) throws SQLException {
            UsuarioRolAcceso usuarioRolAcceso = new UsuarioRolAcceso();
            usuarioRolAcceso.setEmpresaId(rs.getLong("empresa_id"));
            usuarioRolAcceso.setRolId(rs.getLong("rol_id"));
            usuarioRolAcceso.setRolNombre(rs.getString("rol_nombre"));
            usuarioRolAcceso.setAccesos(rs.getString("accesos"));
            usuarioRolAcceso.setNumeroUsuarios(rs.getLong("numero_usuarios"));
            usuarioRolAcceso.setNombres(rs.getString("nombres"));
            usuarioRolAcceso.setApellido(rs.getString("apellidos"));
            usuarioRolAcceso.setEmail(rs.getString("email"));

            return usuarioRolAcceso;
        }
    }
}
