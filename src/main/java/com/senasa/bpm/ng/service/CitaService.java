package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.CitaPaciente;

import java.util.List;

public interface CitaService {
    List<CitaIa> obtenerCitasPorDoctor(String emailDoctor);
    List<CitaPaciente> obtenerCitasPorPaciente(String emailPaciente);
    void agendarCita(CitaIa cita);
}

