package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.dao.rowmapper.User2RowMapper;
import com.senasa.bpm.ng.dao.rowmapper.UserRowMapper;
import com.senasa.bpm.ng.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String query = "SELECT * FROM users WHERE username = ?";
        User user = jdbcTemplate.queryForObject(query, new Object[]{username}, new User2RowMapper());

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}

