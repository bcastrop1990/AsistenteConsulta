package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;

public interface ConfiguracionDao {
    ApiResponse<Void> actualizarConfiguracion(ConfiguracionRequest request);
}


