package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.CitaPaciente;
import com.senasa.bpm.ng.model.request.AgendarCitaRequest;
import com.senasa.bpm.ng.model.request.RequestCitaIa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {
      void agendarCita(AgendarCitaRequest cita);
    List<CitaIa> listarTodasCitasRangoFecha(RequestCitaIa request);
    List<LocalDateTime> obtenerHorariosDisponibles(Long doctorId, LocalDate fecha);
    }

