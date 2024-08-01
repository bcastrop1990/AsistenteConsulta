package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.Cita;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CitaRowMapper implements RowMapper<Cita> {
  @Override
  public Cita mapRow(ResultSet rs, int rowNum) throws SQLException {
    Cita cita = new Cita();
    cita.setIdCita(rs.getLong("id_cita"));
    cita.setEmailDoctor(rs.getString("email_doctor"));
    cita.setEmailPaciente(rs.getString("email_paciente"));
    cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
    cita.setDescripcion(rs.getString("descripcion"));
    cita.setDuracion(rs.getInt("duracion"));
    cita.setIdTransaccion(rs.getInt("id_transaccion"));
    return cita;
  }
}
