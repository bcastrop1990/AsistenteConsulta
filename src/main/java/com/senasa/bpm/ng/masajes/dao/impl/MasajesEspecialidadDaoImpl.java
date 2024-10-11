
package com.senasa.bpm.ng.masajes.dao.impl;

import com.senasa.bpm.ng.masajes.dao.MasajesEspecialidadDao;
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
public class MasajesEspecialidadDaoImpl implements MasajesEspecialidadDao {
    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;


    public List<EspecialidadBean> listarEspecialidad(){

        String sql = "SELECT id, descripcion FROM especialidad";
        try {
            return secondaryJdbcTemplate.query(sql, (rs) -> {
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

