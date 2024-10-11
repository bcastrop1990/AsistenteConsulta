
package com.senasa.bpm.ng.masajes.service.impl;

import com.senasa.bpm.ng.masajes.dao.EspecialidadDao;
import com.senasa.bpm.ng.masajes.model.EspecialidadBean;
import com.senasa.bpm.ng.masajes.service.EspecialidadService;
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
