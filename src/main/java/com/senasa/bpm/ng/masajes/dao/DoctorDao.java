package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.masajes.model.request.DoctorRequest;
import com.senasa.bpm.ng.masajes.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorCubaMedResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorResponse;

import java.util.List;

public interface MasajesDoctorDao {

    //Medico validarDoctor(DoctorRequest doctor) throws IOException;
    //Medico validarOdontologo(DoctorRequest doctor) throws IOException;
    List<DoctorCubaMedResponse> listarDoctor(Long idEspecialidad, String nombre);

    void configurarDisponibilidadDoctor(DoctorDisponibilidadRequest request);

    public DoctorCubaMedDisponibilidadResponse obtenerDisponibilidadPorCorreo(String email);

    DoctorResponse guardarDoctor(DoctorRequest doctorRequest);

    DoctorResponse editarDoctor(Long id, DoctorRequest doctorRequest);

    List<DoctorResponse> listarTodosLosDoctores();

    public DoctorResponse alternarEstadoDoctor(int id);
}


