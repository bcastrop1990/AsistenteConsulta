package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.bean.EnfermedadesBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnfermedadesPrimariasRowMapper implements RowMapper<EnfermedadesBean> {
  @Override
  public EnfermedadesBean mapRow(ResultSet rs, int i) throws SQLException {
    return EnfermedadesBean.builder()
        .id_EnfPr(rs.getLong("id_EnfPr"))
        .nombre(rs.getString("nombre_enfPr"))
        .descripcion(rs.getString("descripcion_enfPr"))
        .build();
  }
}
