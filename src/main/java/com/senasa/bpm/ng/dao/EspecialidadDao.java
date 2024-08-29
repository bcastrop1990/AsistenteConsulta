package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.EspecialidadBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EspecialidadDao {

    List<EspecialidadBean> listarEspecialidad();

}

