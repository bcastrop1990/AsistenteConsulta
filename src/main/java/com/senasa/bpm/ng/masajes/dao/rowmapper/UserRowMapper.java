package com.senasa.bpm.ng.masajes.dao.rowmapper;

import com.senasa.bpm.ng.masajes.model.bean.UserBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserBean> {
  @Override
  public UserBean mapRow(ResultSet rs, int i) throws SQLException {
    return UserBean.builder()
            .email(rs.getString("email"))
            .nombres(rs.getString("nombres"))
            .ape_Paterno(rs.getString("ape_Paterno"))
            .ape_Materno(rs.getString("ape_Materno"))
            .rol(rs.getString("rol"))
            .build();
  }
}
