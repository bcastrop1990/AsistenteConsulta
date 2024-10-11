package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.Cita;
import com.senasa.bpm.ng.masajes.model.CitaIa;
import com.senasa.bpm.ng.masajes.model.request.AgendarCitaRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MasajesCitaDao {
    void agendarCita(AgendarCitaRequest cita);
    List<Cita> obtenerCitasPorEmailDoctorYFecha(String emailDoctor, LocalDate fecha);
    public List<CitaIa> obtenerTodoCitaRangoFechaEmailDoctor(String emailDoctor, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<LocalDateTime> obtenerHorariosDisponibles(Long doctorId, LocalDate fecha);

    }

