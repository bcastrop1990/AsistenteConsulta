package com.senasa.bpm.ng.dao.rowmapper;
import com.senasa.bpm.ng.model.response.LoginReponse;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
public class LoginUserRowMapper implements RowMapper<LoginReponse> {
    @Override
    public  LoginReponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        LoginReponse loginReponse = new LoginReponse();
        loginReponse.setEmpresaId(rs.getString("empresa_id"));
        loginReponse.setEmail(rs.getString("email"));
        loginReponse.setNombre(rs.getString("nombres"));
        loginReponse.setApellido(rs.getString("apellidos"));
        loginReponse.setRolId(rs.getString("rol_id"));
        loginReponse.setRol(rs.getString("rol_nombre"));
        loginReponse.setAvatar(rs.getString("doctor_imagen"));
        return loginReponse;
    }
}