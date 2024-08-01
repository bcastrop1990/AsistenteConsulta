
package com.senasa.bpm.ng.dao.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;

import com.senasa.bpm.ng.dao.DataDao;
import com.senasa.bpm.ng.dao.EnfermedadDao;
import com.senasa.bpm.ng.dao.rowmapper.*;
import com.senasa.bpm.ng.model.bean.DoctorBean;
import com.senasa.bpm.ng.model.bean.EnfermedadesBean;
import com.senasa.bpm.ng.model.bean.ServiciosBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class DataDaoImpl implements DataDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ServiciosBean> listarEspecialidades() {
        //String sql = "SELECT * FROM especialidad";
        String sql = "SELECT DISTINCT(especialidad.id_especialidad), especialidad.* FROM especialidad inner join doctores on doctores.id_especialidad = especialidad.id_especialidad;";
        try {
            return jdbcTemplate.query(sql, new EspecialidadRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
    @Override
    public List<ServiciosBean> listarServicioPorEsp(Long id)  {
        String sql = "SELECT * FROM servicios where id_especialidad = ?";
        try {
            return jdbcTemplate.query(sql, new ServiciosRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<ServiciosBean> obtenerDetalle(Integer id)  {
        String sql = "SELECT * FROM detalle_servicio where id_servicio = ?";
        try {
            return jdbcTemplate.query(sql, new DetalleServicioRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<DoctorBean> listarDoctores() {
        String sql = "SELECT * FROM doctores";
        try {
            return jdbcTemplate.query(sql, new DoctorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<DoctorBean> listarDoctoresPorEsp(Long id)  {
        String sql = "SELECT * FROM doctores where id_especialidad = ?";
        try {
            return jdbcTemplate.query(sql, new DoctorRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<DoctorBean> listarDoctoresPorEsp(Integer id)  {
        String sql = "SELECT doctores.*, doctor_servicio.* FROM doctores INNER JOIN doctor_servicio ON doctores.id_doctor = doctor_servicio.id_doctor where doctor_servicio.id_servicio = ?";
        try {
            return jdbcTemplate.query(sql, new DoctorServicioRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
}
