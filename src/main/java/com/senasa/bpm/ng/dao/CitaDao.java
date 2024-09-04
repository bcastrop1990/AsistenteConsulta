package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.CitaPaciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaDao {
    void agendarCita(CitaIa cita);
    List<Cita> obtenerCitasPorEmailDoctorYFecha(String emailDoctor, LocalDate fecha);
    public List<CitaIa> obtenerTodoCitaRangoFechaEmailDoctor(String emailDoctor, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<LocalDateTime> obtenerHorariosDisponibles(Long doctorId, LocalDate fecha);

    }

