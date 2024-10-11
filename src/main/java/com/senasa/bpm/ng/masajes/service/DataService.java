package com.senasa.bpm.ng.masajes.service;

import com.senasa.bpm.ng.masajes.model.response.DetalleServicioResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorResponse;
import com.senasa.bpm.ng.masajes.model.response.EspecialidadResponse;
import com.senasa.bpm.ng.masajes.model.response.ServicioResponse;

import java.util.List;

public interface DataService {

    List<EspecialidadResponse> listarEspecialidades();
    List<ServicioResponse> listarServicioPorEsp(Long id_especialidad);
    List<DetalleServicioResponse> obtenerDetalle(Integer id_especialidad);
    List<DoctorResponse> listarDoctores();
    List<DoctorResponse> listarDoctoresPorEsp(Long id_servicio);
    List<DoctorResponse> listarDoctoresPorSrvc(Integer id_servicio);

}
