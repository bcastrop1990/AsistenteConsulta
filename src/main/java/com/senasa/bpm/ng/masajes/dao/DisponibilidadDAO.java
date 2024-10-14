package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.Disponibilidad;
import com.senasa.bpm.ng.masajes.model.request.DisponibilidadDoctorRequest;

import java.util.List;
import java.util.Optional;

public interface DisponibilidadDAO {
    void guardar(DisponibilidadDoctorRequest disponibilidad);
    List<Disponibilidad> obtenerDisponibilidadPorEmailDoctor(String emailDoctor);
    // Método adicional para buscar y posiblemente actualizar la disponibilidad existente.
    Optional<Disponibilidad> encontrarPorDoctorYDia(String emailDoctor, int diaSemana);
}


