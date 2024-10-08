
package com.senasa.bpm.ng.dao.impl;

//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.senasa.bpm.ng.dao.CitasDao;
import com.senasa.bpm.ng.dao.TokenDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class TokenDaoImpl implements TokenDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public String obtenerMerchantId() {
        String sql = "SELECT token_value FROM tokens WHERE id = 1";

        return jdbcTemplate.queryForObject(sql, String.class);
    }
    @Override
    public String obtenerSecretKey() {
        String sql = "SELECT token_value FROM tokens WHERE id = 2";

        return jdbcTemplate.queryForObject(sql, String.class);
    }


}
