package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.Disponibilidad;
import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.DisponibilidadDoctorRequest;
import com.senasa.bpm.ng.model.request.DoctorRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DoctorDao {

    Medico validarDoctor(DoctorRequest doctor) throws IOException;
    Medico validarOdontologo(DoctorRequest doctor) throws IOException;
}


