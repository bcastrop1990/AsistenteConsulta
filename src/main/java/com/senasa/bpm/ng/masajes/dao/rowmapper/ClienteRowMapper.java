package com.senasa.bpm.ng.masajes.dao.rowmapper;

import com.senasa.bpm.ng.masajes.model.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper<Cliente> {

  @Override
  public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Cliente.builder()
            .id(rs.getLong("id"))
            .nombre_completo(rs.getString("nombre_completo"))
            .estado(rs.getString("estado"))
            .dni(rs.getString("dni"))
            .tipo_compra(rs.getString("tipo_compra"))
            .cuota_inicial(rs.getString("cuota_inicial"))
            .modelo(rs.getString("modelo"))
            .marca(rs.getString("marca"))
            .celular(rs.getString("celular"))
            .ubicacion(rs.getString("ubicacion"))
            .activo(rs.getString("activo"))
            .fecha(rs.getDate("fecha"))
            .email(rs.getString("email"))
            .build();
  }
}
