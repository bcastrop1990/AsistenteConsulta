package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.Cliente;
import com.senasa.bpm.ng.model.Marca;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper<Cliente> {

  @Override
  public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Cliente.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .celular(rs.getString("celular"))
            .categoria(rs.getString("nombre_categoria"))
            .build();
  }
}
