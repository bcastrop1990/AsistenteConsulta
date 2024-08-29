
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
@Slf4j
public class CitaDaoImpl implements CitaDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public void agendarCita(CitaIa cita) {

        // Calcular fecha y hora final de la cita
        LocalDateTime fechaHoraFinal = cita.getFechaHora().plusMinutes(cita.getDuracion());

        // Consulta para verificar si ya existe una cita con la misma fecha para el doctor
        String sqlCheck = "SELECT COUNT(*) FROM citas WHERE dni = ? AND DATE(fecha_hora) = ?";
        int count = jdbcTemplate.queryForObject(sqlCheck, new Object[]{
                cita.getDni(),
                cita.getFechaHora().toLocalDate()
        }, Integer.class);

        if (count > 0) {
            String sqlUpdate = "UPDATE citas SET email_doctor = ?, dni = ?, nombre_completo = ?, fecha_hora = ?, fechahoraFinal = ?, descripcion = ?, costo = ? WHERE email_doctor = ? AND DATE(fecha_hora) = ?";
            jdbcTemplate.update(sqlUpdate,
                    cita.getEmailDoctor(),
                    cita.getDni(),
                    cita.getNombre_completo(),
                    Timestamp.valueOf(cita.getFechaHora()),
                    Timestamp.valueOf(fechaHoraFinal),
                    cita.getDescripcion(),
                    cita.getCosto(),
                    cita.getEmailDoctor(),
                    cita.getFechaHora().toLocalDate());

        } else {
            String sqlInsert = "INSERT INTO citas (email_doctor, dni, nombre_completo, fecha_hora, fechahoraFinal, duracion, descripcion, costo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlInsert,
                    cita.getEmailDoctor(),
                    cita.getDni(),
                    cita.getNombre_completo(),
                    Timestamp.valueOf(cita.getFechaHora()),
                    Timestamp.valueOf(fechaHoraFinal),
                    cita.getDuracion(),
                    cita.getDescripcion(),
                    cita.getCosto());
        }
    }


    public List<CitaIa> obtenerTodoCitaRangoFechaEmailDoctor(String emailDoctor, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        String sql;
        List<Object> params = new ArrayList<>();

        // Construir la consulta SQL base
        StringBuilder sqlBuilder = new StringBuilder("SELECT id, email_doctor, dni, nombre_completo, fecha_hora, fechahoraFinal, duracion, descripcion, costo FROM citas WHERE ");

        // Agregar filtro por correo electrónico si se proporciona
        if (emailDoctor != null && !emailDoctor.isEmpty()) {
            sqlBuilder.append("email_doctor = ? AND ");
            params.add(emailDoctor);
        }

        // Agregar filtro por rango de fechas
        sqlBuilder.append("fecha_hora BETWEEN ? AND ?");
        params.add(Timestamp.valueOf(fechaInicio));
        params.add(Timestamp.valueOf(fechaFin));

        // Convertir StringBuilder a String
        sql = sqlBuilder.toString();

        return jdbcTemplate.query(sql, params.toArray(), new RowMapper<CitaIa>() {
            @Override
            public CitaIa mapRow(ResultSet rs, int rowNum) throws SQLException {
                CitaIa cita = new CitaIa();
                cita.setId(rs.getInt("id"));
                cita.setEmailDoctor(rs.getString("email_doctor"));
                cita.setDni(rs.getString("dni"));
                cita.setNombre_completo(rs.getString("nombre_completo"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setFechahoraFinal(rs.getTimestamp("fechahoraFinal").toLocalDateTime());
                cita.setDuracion(rs.getInt("duracion"));
                cita.setDescripcion(rs.getString("descripcion"));
                cita.setCosto(rs.getBigDecimal("costo"));
                return cita;
            }
        });
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

