package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.EspecialidadBean;
import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedResponse;

import java.io.IOException;
import java.util.List;

public interface DoctorService {

    Medico validarDoctor(DoctorRequest request) throws IOException;
    //void crearDoctor(ClinicaRequest request);
    List<DoctorCubaMedResponse> listarDoctor(Long idEspecialidad, String nombre);
    void configurarDisponibilidadDoctor(DoctorDisponibilidadRequest request);
    DoctorCubaMedDisponibilidadResponse obtenerDisponibilidadPorCorreo(String email);
}
