package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.Marca;
import com.senasa.bpm.ng.model.bean.ServiciosBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarcaRowMapper implements RowMapper<Marca> {

  @Override
  public Marca mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Marca.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .build();
  }
}
