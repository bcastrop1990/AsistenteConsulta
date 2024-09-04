
package com.senasa.bpm.ng.service.impl;

import com.senasa.bpm.ng.dao.CitaDao;
import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.CitaPaciente;
import com.senasa.bpm.ng.model.request.RequestCitaIa;
import com.senasa.bpm.ng.service.CitaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaDao citaDAO;




    @Override
    public void agendarCita(CitaIa cita) {
        citaDAO.agendarCita(cita);
    }

    public List<CitaIa> listarTodasCitasRangoFecha(RequestCitaIa request){
        return citaDAO.obtenerTodoCitaRangoFechaEmailDoctor(request.getEmailDoctor(),  request.getFechaInicio(),  request.getFechaFinal());
    }


    public List<LocalDateTime> obtenerHorariosDisponibles(Long doctorId, LocalDate fecha){
        return citaDAO.obtenerHorariosDisponibles(doctorId, fecha);
    }

}
