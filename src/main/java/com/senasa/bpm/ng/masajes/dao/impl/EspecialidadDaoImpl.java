
package com.senasa.bpm.ng.masajes.dao.impl;

import com.senasa.bpm.ng.masajes.dao.EspecialidadDao;
import com.senasa.bpm.ng.masajes.model.EspecialidadBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@AllArgsConstructor
@Service
@Slf4j
public class EspecialidadDaoImpl implements EspecialidadDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<EspecialidadBean> listarEspecialidad(){

        String sql = "SELECT id, descripcion FROM especialidad";
        try {
            return jdbcTemplate.query(sql, (rs) -> {
                List<EspecialidadBean> especialidades = new ArrayList<>();
                while (rs.next()) {
                    EspecialidadBean especialidad = new EspecialidadBean();
                    especialidad.setId(rs.getInt("id"));
                    especialidad.setDescripcion(rs.getString("descripcion"));
                    especialidades.add(especialidad);
                }
                return especialidades;
            });
        } catch (EmptyResultDataAccessException e) {
            // Retornar una lista vacía si no hay resultados
            return Collections.emptyList();
        }
    }


}

