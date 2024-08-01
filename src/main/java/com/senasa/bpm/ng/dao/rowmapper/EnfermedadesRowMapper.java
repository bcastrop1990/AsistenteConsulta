package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.bean.EnfermedadesBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnfermedadesRowMapper implements RowMapper<EnfermedadesBean> {
  @Override
  public EnfermedadesBean mapRow(ResultSet rs, int i) throws SQLException {
    return EnfermedadesBean.builder()
            .id_Enf(rs.getLong("id_Enf"))
            .id_EnfPr(rs.getLong("id_EnfPr"))
            .nombre(rs.getString("nombre_enf"))
            .descripcion(rs.getString("descripcion_enf"))
            .build();
  }
}
