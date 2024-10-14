package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.bean.DoctorBean;
import com.senasa.bpm.ng.masajes.model.bean.ServiciosBean;

import java.util.List;

public interface AccesoDao {
    List<ServiciosBean> listarEspecialidades();
    List<ServiciosBean> listarServicioPorEsp(Long id_especialidad);
    List<ServiciosBean> obtenerDetalle(Integer id_especialidad);
    List<DoctorBean> listarDoctores();
    List<DoctorBean> listarDoctoresPorEsp(Long id_especialidad);
    List<DoctorBean> listarDoctoresPorEsp(Integer id);
}
