package com.senasa.bpm.ng.masajes.dao.rowmapper;

import com.senasa.bpm.ng.masajes.model.bean.ServiciosBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EspecialidadRowMapper implements RowMapper<ServiciosBean> {
  @Override
  public ServiciosBean mapRow(ResultSet rs, int i) throws SQLException {
    return ServiciosBean.builder()
            .id_especialidad(rs.getLong("id_especialidad"))
            .nombre(rs.getString("nombre_especialidad"))
            .imagen_url(rs.getString("imagen_url"))
            .build();
  }
}
