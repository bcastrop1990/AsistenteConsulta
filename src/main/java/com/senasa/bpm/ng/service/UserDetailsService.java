package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.request.AgendarCitaRequest;
import com.senasa.bpm.ng.model.request.RequestCitaIa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserDetailsService {
      void agendarCita(AgendarCitaRequest cita);
    List<CitaIa> listarTodasCitasRangoFecha(RequestCitaIa request);
    public List<LocalDateTime> obtenerHorariosDisponibles(Long doctorId, LocalDate fecha);
    }
