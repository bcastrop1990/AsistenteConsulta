package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.bean.EnfermedadesBean;
import com.senasa.bpm.ng.model.bean.ServiciosBean;
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
