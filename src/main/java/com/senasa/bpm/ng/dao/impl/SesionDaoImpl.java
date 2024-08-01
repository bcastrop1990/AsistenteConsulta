
package com.senasa.bpm.ng.dao.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.senasa.bpm.ng.dao.SesionDao;
import com.senasa.bpm.ng.dao.rowmapper.DoctorRowMapper;
import com.senasa.bpm.ng.dao.rowmapper.UserRowMapper;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.bean.UserBean;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class SesionDaoImpl implements SesionDao {

    private JdbcTemplate jdbcTemplate;
    @Override
    public String crearUsuario(UsuarioRequest request) {
        String email = request.getEmail();
        String sqlCount = "SELECT COUNT(*) FROM user WHERE email = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, email) >= 1){
            throw new ApiValidateException("El correo "+email+" ya se encuentra registrado.");
        }

        String sql = "INSERT INTO user (email, password, nombres, ape_Paterno, ape_Materno, rol) VALUES (?, ?, ?, ?, ?, ?)";
        return String.valueOf(jdbcTemplate.update(sql, email, request.getPassword(), request.getNombres(),
                request.getApe_Paterno(), request.getApe_Materno(),request.getRol()));
    }

    public boolean validarCodigo(String email, String codigo) {
        String sql = "SELECT fecha_limite FROM codigo_validacion WHERE email = ? AND codigo = ?";
        ZoneId zoneId = ZoneId.of("America/Lima");
        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);

        try {
            Timestamp timestamp = jdbcTemplate.queryForObject(sql, new Object[]{email, codigo}, Timestamp.class);

            // Convert the timestamp to a ZonedDateTime in the system's default time zone
            LocalDateTime timeOfCode = timestamp.toLocalDateTime();
            ZonedDateTime zonedTimeOfCode = timeOfCode.atZone(ZoneId.systemDefault()).withZoneSameInstant(zoneId);

            return zonedTimeOfCode.plusMinutes(5).isAfter(currentTime);
        } catch (EmptyResultDataAccessException ex) {
            throw new ApiValidateException("El codigo ingresado es invalido.", ex);
        }
    }

    @Override
    public List<UserBean> iniciar(String email, String password) {

        String sqlCount1 = "SELECT COUNT(*) FROM user WHERE email = ?";
        if (jdbcTemplate.queryForObject(sqlCount1, Integer.class, email) == 0){
            throw new ApiValidateException("El correo "+email+" no se encuentra registrado.");
        }
        String sqlCount = "SELECT COUNT(*) FROM user WHERE email = ? and password = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, email, password) == 0){
            throw new ApiValidateException("Contraseña incorrecta.");
        }
        String sql = "SELECT * FROM user where email = ?";
        try {
            return jdbcTemplate.query(sql, new UserRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}

    }
    @Override
    public String obtenerRefreshToken(String email) {
        String sql = "SELECT password FROM user WHERE email = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
    }
/*
    @Override
    public void enviarCodigo(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }*/
    @Override
    public void relacionarImagenesConClinica(String url, int id) {
        String sqlCount = "SELECT COUNT(*) FROM imagen_vista_clinica WHERE url = ?";
        if (!(jdbcTemplate.queryForObject(sqlCount, Integer.class, url) >= 1)){
            String sql = "INSERT INTO clinica (id_clinica, url) VALUES (?, ?, 0, ?, ?)";
            jdbcTemplate.update(sql, id, url);
        }
    }

    @Override
    public int crearClinica(ClinicaRequest request) {
        String ruc = request.getRuc();
        String sqlCount = "SELECT COUNT(*) FROM clinica WHERE ruc = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, ruc) >= 1){
            throw new ApiValidateException("El centro medico con el ruc "+ruc+" ya se encuentra registrado.");
        }
        String sql = "INSERT INTO clinica (nombre, ruc, estado, ubi, imagen) VALUES (?, ?, 0, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.getNombreClinica());
            ps.setString(2, ruc);
            ps.setString(3, request.getUbi());
            ps.setString(4, request.getImagen());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public String guardarCodigoVerificacion(String email, String codigo) {

        String sqlCount1 = "SELECT COUNT(*) FROM user WHERE email = ?";
        if (jdbcTemplate.queryForObject(sqlCount1, Integer.class, email) >= 1){
            throw new ApiValidateException("El correo "+email+" ya se encuentra registrado.");
        }
        String sqlCount = "SELECT COUNT(*) FROM codigo_validacion WHERE email = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, email) > 0){
            String sqlUpdate = "UPDATE codigo_validacion SET codigo = ?, fecha_limite =  CONVERT_TZ(NOW(), @@session.time_zone, 'America/Lima') WHERE email = ?";
            jdbcTemplate.update(sqlUpdate, codigo, email);
            return "Se volvio a enviar el codigó de verificación al correo "+email;
        }else {
            String sql = "INSERT INTO codigo_validacion (email, codigo, fecha_limite) VALUES (?, ?,  CONVERT_TZ(NOW(), @@session.time_zone, 'America/Lima'))";
            jdbcTemplate.update(sql, email, codigo);
            return "Se envio el codigó de verificación al correo "+email;
        }
    }


}
