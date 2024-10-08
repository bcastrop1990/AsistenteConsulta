
package com.senasa.bpm.ng.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.dao.DisponibilidadDAO;
import com.senasa.bpm.ng.dao.rowmapper.DisponibilidadRowMapper;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.Disponibilidad;
import com.senasa.bpm.ng.model.request.DisponibilidadDoctorRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class DisponibilidadDAOImpl implements DisponibilidadDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void guardar(DisponibilidadDoctorRequest disponibilidad) {
        try {
            Optional<Disponibilidad> existente = this.encontrarPorDoctorYDia(disponibilidad.getEmailDoctor(), disponibilidad.getDiaSemana());

            if (existente.isPresent()) {
                // Actualizar registro existente
                String sql = "UPDATE disponibilidad SET hora_inicio = ?, hora_fin = ? WHERE id = ?";
                jdbcTemplate.update(sql, disponibilidad.getHoraInicio(), disponibilidad.getHoraFin(), existente.get().getId());
            } else {
                // Insertar nuevo registro si no existe
                String sql = "INSERT INTO disponibilidad (email_doctor, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(sql, disponibilidad.getEmailDoctor(), disponibilidad.getDiaSemana(), disponibilidad.getHoraInicio(), disponibilidad.getHoraFin());
            }
        } catch (DataAccessException e) {
            throw new ApiValidateException("Error al acceder a la base de datos", e);
        }
    }



    @Override
    public List<Disponibilidad> obtenerDisponibilidadPorEmailDoctor(String emailDoctor) {
        String sql = "SELECT * FROM disponibilidad WHERE email_doctor = ?";
        return jdbcTemplate.query(sql, new Object[]{emailDoctor}, new DisponibilidadRowMapper());
    }


    @Override
    public Optional<Disponibilidad> encontrarPorDoctorYDia(String emailDoctor, int diaSemana) {
        String sql = "SELECT * FROM disponibilidad WHERE email_doctor = ? AND dia_semana = ?";
        return jdbcTemplate.query(sql, new Object[]{emailDoctor, diaSemana}, new DisponibilidadRowMapper())
                .stream()
                .findFirst();
    }
}
