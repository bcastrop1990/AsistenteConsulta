package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.CitaPaciente;

import java.time.LocalDate;
import java.util.List;

public interface CitaDao {
    List<CitaIa> obtenerCitasPorEmailDoctor(String emailDoctor);
    List<CitaPaciente> obtenerCitasPorPaciente(String emailPaciente);
    void agendarCita(CitaIa cita);
    List<Cita> obtenerCitasPorEmailDoctorYFecha(String emailDoctor, LocalDate fecha);
}

