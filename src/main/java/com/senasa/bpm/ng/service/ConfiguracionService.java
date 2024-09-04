package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedResponse;

import java.io.IOException;
import java.util.List;

public interface ConfiguracionService {

    ApiResponse<Void> actualizarConfiguracion(ConfiguracionRequest request);
}