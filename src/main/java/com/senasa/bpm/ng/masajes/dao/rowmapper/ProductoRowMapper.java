package com.senasa.bpm.ng.masajes.dao.rowmapper;

import com.senasa.bpm.ng.masajes.model.Producto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoRowMapper implements RowMapper<Producto> {
  @Override
  public Producto mapRow(ResultSet rs, int i) throws SQLException {
    return Producto.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .marca(rs.getLong("marca"))
            .desc(rs.getString("descrp"))
            .nombre_marca(rs.getString("nombre_marca"))
            .codigo(rs.getString("codigo"))
            .precio(rs.getBigDecimal("precio"))
            .imagen(rs.getString("imagen"))
            .build();
  }
}