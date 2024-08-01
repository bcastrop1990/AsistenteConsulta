
package com.senasa.bpm.ng.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.dao.CitaDao;
import com.senasa.bpm.ng.model.Cita;
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
    public List<Cita> obtenerCitasPorEmailDoctor(String emailDoctor) {
        String sql = "SELECT * FROM citas WHERE email_doctor = ? and id_transaccion is not null";
        return jdbcTemplate.query(sql, new Object[]{emailDoctor}, new BeanPropertyRowMapper<>(Cita.class));
    }

    @Override
    public List<CitaPaciente> obtenerCitasPorPaciente(String emailPaciente) {
        String sql = "SELECT citas.*, servicios.imagen_url, servicios.nombre_servicio FROM citas inner join servicios on citas.id_servicio = servicios.id_servicio where citas.email_paciente = ?";
        return jdbcTemplate.query(sql, new Object[]{emailPaciente}, new BeanPropertyRowMapper<>(CitaPaciente.class));
    }

    @Override
    public void agendarCita(Cita cita) {
        if(cita.getIdCita() != null && cita.getIdTransaccion() != null){

            String sql = "UPDATE citas SET id_transaccion = ? WHERE id_cita = ?";
            jdbcTemplate.update(sql, cita.getIdTransaccion(), cita.getIdCita());

        } else {
            String sql = "INSERT INTO citas (email_doctor, email_paciente, fecha_hora, descripcion, duracion, id_transaccion, id_servicio, monto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, cita.getEmailDoctor(), cita.getEmailPaciente(),
                    Timestamp.valueOf(cita.getFechaHora()), cita.getDescripcion(),
                    cita.getDuracion(), cita.getIdTransaccion(), cita.getId_servicio(), cita.getMonto());
        }

    }

    @Override
    public List<Cita> obtenerCitasPorEmailDoctorYFecha(String emailDoctor, LocalDate fecha) {
        LocalDateTime startOfDay = fecha.atStartOfDay();
        LocalDateTime endOfDay = fecha.plusDays(1).atStartOfDay();

        String sql = "SELECT * FROM citas WHERE id_transaccion is not null and email_doctor = ? AND fecha_hora BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new Object[]{emailDoctor, Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay)},
                new BeanPropertyRowMapper<>(Cita.class));
    }

}

