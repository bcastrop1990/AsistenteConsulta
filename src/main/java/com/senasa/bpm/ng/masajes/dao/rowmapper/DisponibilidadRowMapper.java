package com.senasa.bpm.ng.masajes.dao.rowmapper;

import com.senasa.bpm.ng.masajes.model.Disponibilidad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisponibilidadRowMapper implements RowMapper<Disponibilidad> {
  @Override
  public Disponibilidad mapRow(ResultSet rs, int rowNum) throws SQLException {
    Disponibilidad disponibilidad = new Disponibilidad();
    disponibilidad.setId(rs.getLong("id"));
    disponibilidad.setEmailDoctor(rs.getString("email_doctor"));
    disponibilidad.setDiaSemana(rs.getInt("dia_semana"));
    disponibilidad.setHoraInicio(rs.getTime("hora_inicio"));
    disponibilidad.setHoraFin(rs.getTime("hora_fin"));

    return disponibilidad;
  }
}
