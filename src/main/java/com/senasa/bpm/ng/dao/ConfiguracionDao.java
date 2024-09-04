package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;

public interface ConfiguracionDao {
    ApiResponse<Void> actualizarConfiguracion(ConfiguracionRequest request);
}


