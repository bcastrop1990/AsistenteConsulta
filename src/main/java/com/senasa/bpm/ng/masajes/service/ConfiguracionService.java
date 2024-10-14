package com.senasa.bpm.ng.masajes.service;

import com.senasa.bpm.ng.masajes.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;

public interface ConfiguracionService {

    ApiResponse<Void> actualizarConfiguracion(ConfiguracionRequest request);
}