package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.CitaPaciente;
import com.senasa.bpm.ng.model.request.RequestCitaIa;

import java.util.List;

public interface CitaService {
      void agendarCita(CitaIa cita);
    List<CitaIa> listarTodasCitasRangoFecha(RequestCitaIa request);
}

