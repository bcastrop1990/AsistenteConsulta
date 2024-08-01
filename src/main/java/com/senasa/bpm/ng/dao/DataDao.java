package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.bean.DoctorBean;
import com.senasa.bpm.ng.model.bean.ServiciosBean;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.EspecialidadResponse;

import java.util.List;

public interface DataDao {
    List<ServiciosBean> listarEspecialidades();
    List<ServiciosBean> listarServicioPorEsp(Long id_especialidad);
    List<ServiciosBean> obtenerDetalle(Integer id_especialidad);
    List<DoctorBean> listarDoctores();
    List<DoctorBean> listarDoctoresPorEsp(Long id_especialidad);
    List<DoctorBean> listarDoctoresPorEsp(Integer id);

}
