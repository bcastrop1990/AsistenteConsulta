
package com.senasa.bpm.ng.masajes.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.masajes.dao.MasajesCitaDao;
import com.senasa.bpm.ng.masajes.model.Cita;
import com.senasa.bpm.ng.masajes.model.CitaIa;
import com.senasa.bpm.ng.masajes.model.request.AgendarCitaRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Service
@Slf4j
public class MasajesCitaDaoImpl implements MasajesCitaDao {
    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;



    @Override
    public void agendarCita(AgendarCitaRequest cita) {
        System.out.println("cita.duracion: " + cita.getDuracion());
        // Calcular fecha y hora final de la cita
        LocalDateTime fechaHoraFinal = cita.getFechaHoraInicio().plusMinutes(cita.getDuracion());

        // Consulta para verificar si ya existe una cita con la misma fecha para el doctor
        String sqlCheck = "SELECT COUNT(*) FROM citas WHERE dni = ? AND DATE(fechaHoraInicio) = ? AND doctorId = ?";
        int count = secondaryJdbcTemplate.queryForObject(sqlCheck, new Object[]{
                cita.getDni(),
                cita.getFechaHoraInicio().toLocalDate(),
                cita.getDoctorId()
        }, Integer.class);

        if (count > 0) {
            String sqlUpdate = "UPDATE citas SET email_doctor = ?, dni = ?, nombre_completo = ?, fechaHoraInicio = ?, fechahoraFinal = ?, descripcion = ?, costo = ?, doctorId = ? WHERE email_doctor = ? AND DATE(fechaHoraInicio) = ? AND doctorId = ?";
            secondaryJdbcTemplate.update(sqlUpdate,
                    cita.getEmailDoctor(),
                    cita.getDni(),
                    cita.getNombre_completo(),
                    Timestamp.valueOf(cita.getFechaHoraInicio()),
                    Timestamp.valueOf(fechaHoraFinal),
                    cita.getDescripcion(),
                    cita.getCosto(),
                    cita.getDoctorId(),
                    cita.getEmailDoctor(),
                    cita.getFechaHoraInicio().toLocalDate(),
                    cita.getDoctorId());
        } else {
            String sqlInsert = "INSERT INTO citas (email_doctor, dni, nombre_completo, fechaHoraInicio, fechahoraFinal, duracion, descripcion, costo, doctorId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            secondaryJdbcTemplate.update(sqlInsert,
                    cita.getEmailDoctor(),
                    cita.getDni(),
                    cita.getNombre_completo(),
                    Timestamp.valueOf(cita.getFechaHoraInicio()),
                    Timestamp.valueOf(fechaHoraFinal),
                    cita.getDuracion(),
                    cita.getDescripcion(),
                    cita.getCosto(),
                    cita.getDoctorId());
        }
    }


    public List<CitaIa> obtenerTodoCitaRangoFechaEmailDoctor(String emailDoctor, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        String sql;
        List<Object> params = new ArrayList<>();

        // Construir la consulta SQL base con JOINs para incluir el color de doctor_disponibilidad
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT c.id, c.email_doctor, c.dni, c.nombre_completo, c.fechaHoraInicio, c.fechahoraFinal, " +
                        "c.duracion, c.descripcion, c.costo, dd.color " +
                        "FROM citas c " +
                        "JOIN doctores d ON c.email_doctor = d.email " +
                        "JOIN doctor_disponibilidad dd ON d.id = dd.idDoctor WHERE "
        );

        // Agregar filtro por correo electrónico si se proporciona
        if (emailDoctor != null && !emailDoctor.isEmpty()) {
            sqlBuilder.append("c.email_doctor = ? AND ");
            params.add(emailDoctor);
        }

        // Agregar filtro por rango de fechas
        sqlBuilder.append("c.fechaHoraInicio BETWEEN ? AND ?");
        params.add(Timestamp.valueOf(fechaInicio));
        params.add(Timestamp.valueOf(fechaFin));

        // Convertir StringBuilder a String
        sql = sqlBuilder.toString();

        // Imprimir la consulta SQL final y los parámetros
        System.out.println("SQL Query: " + sql);
        System.out.println("Parameters: " + params);

        return secondaryJdbcTemplate.query(sql, params.toArray(), new RowMapper<CitaIa>() {
            @Override
            public CitaIa mapRow(ResultSet rs, int rowNum) throws SQLException {
                CitaIa cita = new CitaIa();
                cita.setId(rs.getInt("id"));
                cita.setEmailDoctor(rs.getString("email_doctor"));
                cita.setDni(rs.getString("dni"));
                cita.setNombre_completo(rs.getString("nombre_completo"));
                cita.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                cita.setFechahoraFinal(rs.getTimestamp("fechahoraFinal").toLocalDateTime());
                cita.setDuracion(rs.getInt("duracion"));
                cita.setDescripcion(rs.getString("descripcion"));
                cita.setCosto(rs.getBigDecimal("costo"));
                cita.setColor(rs.getString("color")); // Asignar el color de doctor_disponibilidad

                // Imprimir la cita que se ha mapeado
                System.out.println("Cita obtenida: " + cita);

                return cita;
            }
        });
    }






    @Override
    public List<Cita> obtenerCitasPorEmailDoctorYFecha(String emailDoctor, LocalDate fecha) {
        LocalDateTime startOfDay = fecha.atStartOfDay();
        LocalDateTime endOfDay = fecha.plusDays(1).atStartOfDay();

        String sql = "SELECT * FROM citas WHERE email_doctor = ? AND fechaHoraInicio BETWEEN ? AND ?";
        return secondaryJdbcTemplate.query(sql, new Object[]{emailDoctor, Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay)},
                new BeanPropertyRowMapper<>(Cita.class));
    }


    public List<CitaIa> obtenerCitasPorFecha(String emailDoctor, LocalDate fecha) {
        String sql = "SELECT * FROM citas WHERE email_doctor = ? AND DATE(fechaHoraInicio) = ?";
        return secondaryJdbcTemplate.query(sql, new Object[]{emailDoctor, fecha}, (rs, rowNum) -> {
            return CitaIa.builder()
                    .id(rs.getInt("id"))
                    .emailDoctor(rs.getString("email_doctor"))
                    .dni(rs.getString("dni"))
                    .nombre_completo(rs.getString("nombre_completo"))
                    .fechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime())
                    .duracion(rs.getInt("duracion"))
                    .descripcion(rs.getString("descripcion"))
                    .costo(rs.getBigDecimal("costo"))
                    .fechahoraFinal(rs.getTimestamp("fechahoraFinal").toLocalDateTime())
                    .build();
        });
    }


    @Override
    public List<LocalDateTime> obtenerHorariosDisponibles(Long doctorId, LocalDate fecha) {
        try {
            // Paso 1: Obtener citas programadas para la fecha específica usando doctorId
            String sqlCitas = "SELECT fechaHoraInicio, fechahoraFinal FROM citas WHERE doctorId = ? AND DATE(fechaHoraInicio) = ?";
            List<LocalDateTime[]> citasProgramadas = secondaryJdbcTemplate.query(sqlCitas, new Object[]{doctorId, fecha},
                    (rs, rowNum) -> new LocalDateTime[]{rs.getTimestamp("fechaHoraInicio").toLocalDateTime(), rs.getTimestamp("fechahoraFinal").toLocalDateTime()});

            // Paso 2: Obtener la disponibilidad del doctor para esa fecha (usando doctorId)
            String sqlDisponibilidad = "SELECT hora_inicio_lunes, hora_fin_lunes, hora_inicio_martes, hora_fin_martes, " +
                    "hora_inicio_miercoles, hora_fin_miercoles, hora_inicio_jueves, hora_fin_jueves, " +
                    "hora_inicio_viernes, hora_fin_viernes, hora_inicio_sabado, hora_fin_sabado, " +
                    "hora_inicio_domingo, hora_fin_domingo " +
                    "FROM doctor_disponibilidad WHERE idDoctor = ?";

            Map<String, Object> disponibilidad = secondaryJdbcTemplate.queryForMap(sqlDisponibilidad, doctorId);

            // Verificar si la disponibilidad es null o está vacía
            if (disponibilidad == null || disponibilidad.isEmpty()) {
                System.out.println("No se encontró disponibilidad para el doctor con id " + doctorId);
                return new ArrayList<>();
            }

            // Convertir los valores a LocalTime, asegurándose de que no sean nulos
            Map<String, LocalTime> disponibilidadHorarios = new HashMap<>();
            for (Map.Entry<String, Object> entry : disponibilidad.entrySet()) {
                if (entry.getValue() != null) {
                    disponibilidadHorarios.put(entry.getKey(), ((Time) entry.getValue()).toLocalTime());
                } else {
                    System.out.println("El valor para " + entry.getKey() + " es null");
                }
            }

            // Determinar los nombres de las columnas de inicio y fin según el día de la semana
            DayOfWeek diaSemana = fecha.getDayOfWeek();
            String horaInicioCol = "";
            String horaFinCol = "";

            switch (diaSemana) {
                case MONDAY:
                    horaInicioCol = "hora_inicio_lunes";
                    horaFinCol = "hora_fin_lunes";
                    break;
                case TUESDAY:
                    horaInicioCol = "hora_inicio_martes";
                    horaFinCol = "hora_fin_martes";
                    break;
                case WEDNESDAY:
                    horaInicioCol = "hora_inicio_miercoles";
                    horaFinCol = "hora_fin_miercoles";
                    break;
                case THURSDAY:
                    horaInicioCol = "hora_inicio_jueves";
                    horaFinCol = "hora_fin_jueves";
                    break;
                case FRIDAY:
                    horaInicioCol = "hora_inicio_viernes";
                    horaFinCol = "hora_fin_viernes";
                    break;
                case SATURDAY:
                    horaInicioCol = "hora_inicio_sabado";
                    horaFinCol = "hora_fin_sabado";
                    break;
                case SUNDAY:
                    horaInicioCol = "hora_inicio_domingo";
                    horaFinCol = "hora_fin_domingo";
                    break;
            }

            // Obtener los valores de inicio y fin
            LocalTime horaInicio = disponibilidadHorarios.get(horaInicioCol);
            LocalTime horaFin = disponibilidadHorarios.get(horaFinCol);

            // Verificar si los horarios de inicio y fin no son nulos
            if (horaInicio == null || horaFin == null) {
                System.out.println("No hay disponibilidad registrada para el doctor con id " + doctorId + " el día " + diaSemana);
                return new ArrayList<>();
            }


            // Paso 3: Generar la lista de horarios disponibles en intervalos de media hora
            List<LocalDateTime> horariosDisponibles = new ArrayList<>();
            LocalDateTime horario = fecha.atTime(horaInicio);

            while (horario.isBefore(fecha.atTime(horaFin))) {
                boolean disponible = true;

                // Verificar si el horario está ocupado por alguna cita
                for (LocalDateTime[] cita : citasProgramadas) {
                    if (!(horario.plusMinutes(30).isBefore(cita[0]) || horario.isAfter(cita[1]))) {
                        disponible = false;
                        break;
                    }
                }

                if (disponible) {
                    horariosDisponibles.add(horario);
                }

                horario = horario.plusMinutes(30);
            }

            return horariosDisponibles;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron citas o disponibilidad para el doctor con id " + doctorId + " en la fecha " + fecha);
            return new ArrayList<>();
        } catch (DataAccessException e) {
            System.out.println("Error de acceso a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error de acceso a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al procesar los horarios disponibles: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al procesar los horarios disponibles: " + e.getMessage());
        }
    }


}

