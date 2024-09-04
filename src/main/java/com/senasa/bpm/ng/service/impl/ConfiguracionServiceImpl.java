package com.senasa.bpm.ng.service.impl;

import com.senasa.bpm.ng.dao.ConfiguracionDao;
import com.senasa.bpm.ng.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.service.ConfiguracionService;
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
