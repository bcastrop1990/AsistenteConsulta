package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.response.*;

import java.util.List;

public interface DataService {

    List<EspecialidadResponse> listarEspecialidades();
    List<ServicioResponse> listarServicioPorEsp(Long id_especialidad);
    List<DetalleServicioResponse> obtenerDetalle(Integer id_especialidad);
    List<DoctorResponse> listarDoctores();
    List<DoctorResponse> listarDoctoresPorEsp(Long id_servicio);
    List<DoctorResponse> listarDoctoresPorSrvc(Integer id_servicio);

}
