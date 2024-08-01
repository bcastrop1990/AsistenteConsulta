package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.bean.DoctorBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorServicioRowMapper implements RowMapper<DoctorBean> {
  @Override
  public DoctorBean mapRow(ResultSet rs, int i) throws SQLException {
    return DoctorBean.builder()
            .id_especialidad(rs.getLong("doctores.id_especialidad"))
            .nombre_doctor(rs.getString("doctores.nombre_doctor"))
            .imagen_url(rs.getString("doctores.imagen_url"))
            .email(rs.getString("doctores.email"))
            .build();
  }
}
