
package com.senasa.bpm.ng.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.dao.CitaDao;
import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.CitaPaciente;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Service
@Slf4j
public class CitaDaoImpl implements CitaDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CitaIa> obtenerCitasPorEmailDoctor(String emailDoctor) {
        String sql = "SELECT * FROM citas WHERE email_doctor = ?";
        return jdbcTemplate.query(sql, new Object[]{emailDoctor}, new BeanPropertyRowMapper<>(CitaIa.class));
    }

    @Override
    public List<CitaPaciente> obtenerCitasPorPaciente(String emailPaciente) {
        String sql = "SELECT citas.*, servicios.imagen_url, servicios.nombre_servicio FROM citas inner join servicios on citas.id_servicio = servicios.id_servicio where citas.email_paciente = ?";
        return jdbcTemplate.query(sql, new Object[]{emailPaciente}, new BeanPropertyRowMapper<>(CitaPaciente.class));
    }

    @Override
    public void agendarCita(CitaIa cita) {

        // Consulta para verificar si ya existe una cita con la misma fecha para el doctor
        String sqlCheck = "SELECT COUNT(*) FROM citas WHERE dni = ? AND DATE(fecha_hora) = ?";
        int count = jdbcTemplate.queryForObject(sqlCheck, new Object[]{
                cita.getDni(),
                cita.getFechaHora().toLocalDate()
        }, Integer.class);

        if (count > 0) {
            String sqlUpdate = "UPDATE citas SET email_doctor = ?, dni = ?, nombre_completo = ?, fecha_hora = ?, descripcion = ?, costo = ? WHERE email_doctor = ? AND DATE(fecha_hora) = ?";
            jdbcTemplate.update(sqlUpdate,
                    cita.getEmailDoctor(),
                    cita.getDni(),
                    cita.getNombre_completo(),
                    Timestamp.valueOf(cita.getFechaHora()),
                    cita.getDescripcion(),
                    cita.getCosto(),
                    cita.getEmailDoctor(),
                    cita.getFechaHora().toLocalDate());

        } else {
            String sqlInsert = "INSERT INTO citas (email_doctor, dni, nombre_completo, fecha_hora, duracion, descripcion, costo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlInsert,
                    cita.getEmailDoctor(),
                    cita.getDni(),
                    cita.getNombre_completo(),
                    Timestamp.valueOf(cita.getFechaHora()),
                    cita.getDuracion(),
                    cita.getDescripcion(),
                    cita.getCosto());
        }
    }


    @Override
    public List<Cita> obtenerCitasPorEmailDoctorYFecha(String emailDoctor, LocalDate fecha) {
        LocalDateTime startOfDay = fecha.atStartOfDay();
        LocalDateTime endOfDay = fecha.plusDays(1).atStartOfDay();

        String sql = "SELECT * FROM citas WHERE email_doctor = ? AND fecha_hora BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new Object[]{emailDoctor, Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay)},
                new BeanPropertyRowMapper<>(Cita.class));
    }

}

