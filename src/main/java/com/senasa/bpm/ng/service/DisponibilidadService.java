package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.dao.CitaDao;
import com.senasa.bpm.ng.dao.DisponibilidadDAO;
import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.Disponibilidad;
import com.senasa.bpm.ng.model.request.DisponibilidadDoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class DisponibilidadService {
    @Autowired
    private DisponibilidadDAO disponibilidadDAO;
    @Autowired
    private CitaDao citaDAO;

    public void definirDisponibilidad(DisponibilidadDoctorRequest nuevaDisponibilidad) {
        disponibilidadDAO.guardar(nuevaDisponibilidad);
    }

    public List<LocalDateTime> obtenerHorariosDisponibles(String emailDoctor, LocalDate fecha) {
        DayOfWeek diaDeLaSemana = fecha.getDayOfWeek();
        List<Disponibilidad> disponibilidades = disponibilidadDAO.obtenerDisponibilidadPorEmailDoctor(emailDoctor)
                .stream()
                .filter(d -> d.getDiaSemana() == diaDeLaSemana.getValue())
                .collect(Collectors.toList());

        List<Cita> citasDelDia = citaDAO.obtenerCitasPorEmailDoctorYFecha(emailDoctor, fecha);

        return disponibilidades.stream()
                .flatMap(d -> Stream.iterate(LocalDateTime.of(fecha, d.getHoraInicio().toLocalTime()),
                                tiempo -> tiempo.plusMinutes(30))
                        .limit(ChronoUnit.MINUTES.between(d.getHoraInicio().toLocalTime(), d.getHoraFin().toLocalTime()) / 30)
                        .filter(tiempo -> citasDelDia.stream()
                                .noneMatch(c -> tiempo.equals(c.getFechaHora()) ||
                                        (tiempo.isAfter(c.getFechaHora()) && tiempo.isBefore(c.getFechaHora().plusMinutes(c.getDuracion()))))))
                .collect(Collectors.toList());
    }





}


