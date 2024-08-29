
package com.senasa.bpm.ng.service.impl;

import com.senasa.bpm.ng.dao.CitaDao;
import com.senasa.bpm.ng.dao.EspecialidadDao;
import com.senasa.bpm.ng.model.CitaIa;
import com.senasa.bpm.ng.model.EspecialidadBean;
import com.senasa.bpm.ng.model.request.RequestCitaIa;
import com.senasa.bpm.ng.service.CitaService;
import com.senasa.bpm.ng.service.EspecialidadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EspecialidadServiceImpl implements EspecialidadService {
    @Autowired
    private EspecialidadDao especialidadDao;




    public List<EspecialidadBean> listarEspecialidad(){

        return especialidadDao.listarEspecialidad();
    }


}
