package com.senasa.bpm.ng.masajes.service;

import com.senasa.bpm.ng.masajes.model.CitaIa;
import com.senasa.bpm.ng.masajes.model.request.AgendarCitaRequest;
import com.senasa.bpm.ng.masajes.model.request.RequestCitaIa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {
      void agendarCita(AgendarCitaRequest cita);
    List<CitaIa> listarTodasCitasRangoFecha(RequestCitaIa request);
    public List<LocalDateTime> obtenerHorariosDisponibles(Long doctorId, LocalDate fecha);
    }

