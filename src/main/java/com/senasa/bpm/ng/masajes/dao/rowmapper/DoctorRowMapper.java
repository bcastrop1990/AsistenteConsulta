package com.senasa.bpm.ng.masajes.dao.rowmapper;

import com.senasa.bpm.ng.masajes.model.bean.DoctorBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorRowMapper implements RowMapper<DoctorBean> {
  @Override
  public DoctorBean mapRow(ResultSet rs, int i) throws SQLException {
    return DoctorBean.builder()
            .id_especialidad(rs.getLong("id_especialidad"))
            .nombre_doctor(rs.getString("nombre_doctor"))
            .imagen_url(rs.getString("imagen_url"))
            .email(rs.getString("email"))
            .build();
  }
}
