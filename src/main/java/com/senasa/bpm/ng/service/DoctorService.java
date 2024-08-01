package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.UserResponse;

import java.io.IOException;
import java.util.List;

public interface DoctorService {

    Medico validarDoctor(DoctorRequest request) throws IOException;
    //void crearDoctor(ClinicaRequest request);
}
