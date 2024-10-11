package com.senasa.bpm.ng.masajes.service.impl;

import com.senasa.bpm.ng.masajes.dao.ConfiguracionDao;
import com.senasa.bpm.ng.masajes.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.ConfiguracionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
@Slf4j
public class ConfiguracionServiceImpl implements ConfiguracionService {

    @Autowired
    private ConfiguracionDao configuracionDao;

    public ApiResponse<Void> actualizarConfiguracion(ConfiguracionRequest request) {
       return configuracionDao.actualizarConfiguracion(request);
    }

}
