package com.senasa.bpm.ng.dao.rowmapper;

import com.senasa.bpm.ng.model.bean.ServiciosBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleServicioRowMapper implements RowMapper<ServiciosBean> {
  @Override
  public ServiciosBean mapRow(ResultSet rs, int i) throws SQLException {
    return ServiciosBean.builder()
            .hospitalizacion(rs.getString("hospitalizacion"))
            .duracion(rs.getString("duracion"))
            .anestesia(rs.getString("anestesia"))
            .recuperacion_total(rs.getString("recuperacion_total"))
            .tipo(rs.getString("tipo"))
            .codigo_tipo(rs.getString("codigo_tipo"))
            .sedes(rs.getString("sedes"))
            .nombre_cx(rs.getString("nombre_cx"))
            .build();
  }
}
