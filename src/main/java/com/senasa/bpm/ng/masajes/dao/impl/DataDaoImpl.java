
package com.senasa.bpm.ng.masajes.dao.impl;

import com.senasa.bpm.ng.masajes.dao.DataDao;
import com.senasa.bpm.ng.masajes.dao.rowmapper.*;
import com.senasa.bpm.ng.masajes.model.bean.DoctorBean;
import com.senasa.bpm.ng.masajes.model.bean.ServiciosBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class DataDaoImpl implements DataDao {

    private JdbcTemplate secondaryJdbcTemplate;

    @Override
    public List<ServiciosBean> listarEspecialidades() {
        //String sql = "SELECT * FROM especialidad";
        String sql = "SELECT DISTINCT(especialidad.id_especialidad), especialidad.* FROM especialidad inner join doctores on doctores.id_especialidad = especialidad.id_especialidad;";
        try {
            return secondaryJdbcTemplate.query(sql, new EspecialidadRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
    @Override
    public List<ServiciosBean> listarServicioPorEsp(Long id)  {
        String sql = "SELECT * FROM servicios where id_especialidad = ?";
        try {
            return secondaryJdbcTemplate.query(sql, new ServiciosRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<ServiciosBean> obtenerDetalle(Integer id)  {
        String sql = "SELECT * FROM detalle_servicio where id_servicio = ?";
        try {
            return secondaryJdbcTemplate.query(sql, new DetalleServicioRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<DoctorBean> listarDoctores() {
        String sql = "SELECT * FROM doctores";
        try {
            return secondaryJdbcTemplate.query(sql, new DoctorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<DoctorBean> listarDoctoresPorEsp(Long id)  {
        String sql = "SELECT * FROM doctores where id_especialidad = ?";
        try {
            return secondaryJdbcTemplate.query(sql, new DoctorRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<DoctorBean> listarDoctoresPorEsp(Integer id)  {
        String sql = "SELECT doctores.*, doctor_servicio.* FROM doctores INNER JOIN doctor_servicio ON doctores.id_doctor = doctor_servicio.id_doctor where doctor_servicio.id_servicio = ?";
        try {
            return secondaryJdbcTemplate.query(sql, new DoctorServicioRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
}
