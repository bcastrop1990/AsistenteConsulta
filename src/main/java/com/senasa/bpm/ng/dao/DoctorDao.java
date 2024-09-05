package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.Disponibilidad;
import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.DisponibilidadDoctorRequest;
import com.senasa.bpm.ng.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedResponse;
import com.senasa.bpm.ng.model.response.DoctorResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DoctorDao {

    //Medico validarDoctor(DoctorRequest doctor) throws IOException;
    //Medico validarOdontologo(DoctorRequest doctor) throws IOException;
    List<DoctorCubaMedResponse> listarDoctor(Long idEspecialidad, String nombre);

    void configurarDisponibilidadDoctor(DoctorDisponibilidadRequest request);

    public DoctorCubaMedDisponibilidadResponse obtenerDisponibilidadPorCorreo(String email);

    DoctorResponse guardarDoctor(DoctorRequest doctorRequest);

    DoctorResponse editarDoctor(Long id, DoctorRequest doctorRequest);

    List<DoctorResponse> listarTodosLosDoctores();

    public DoctorResponse alternarEstadoDoctor(Long id);
}


