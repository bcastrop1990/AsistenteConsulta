package com.senasa.bpm.ng.masajes.dao.rowmapper;

import com.senasa.bpm.ng.masajes.model.Venta;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaRowMapper implements RowMapper<Venta> {
  @Override
  public Venta mapRow(ResultSet rs, int rowNum) throws SQLException {
    Venta venta = new Venta();
    venta.setId(rs.getLong("id"));
    venta.setMonto(rs.getDouble("monto"));
    venta.setIdCliente(rs.getLong("id_cliente"));
    venta.setCantidad(rs.getInt("cantidad"));
    venta.setFecha(rs.getDate("fecha"));
    return venta;
  }
}

