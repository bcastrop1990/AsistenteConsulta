package com.senasa.bpm.ng.masajes.service.impl;

import com.senasa.bpm.ng.masajes.dao.SocialManagerDao;
import com.senasa.bpm.ng.masajes.model.EstadoFinanciamiento;
import com.senasa.bpm.ng.masajes.model.MarcaMotoFacil;
import com.senasa.bpm.ng.masajes.model.ModeloMotoFacil;
import com.senasa.bpm.ng.masajes.model.TipoDeCompra;
import com.senasa.bpm.ng.masajes.service.SocialManagerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SocialManagerServiceImpl implements SocialManagerService {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SocialManagerDao socialManagerDao;

    public List<EstadoFinanciamiento> listarEstadosFinanciamiento() {
    return socialManagerDao.listarEstadosFinanciamiento();
    }

    public List<TipoDeCompra> listarTipoCompra() {
        return socialManagerDao.listarTipoCompra();
    }
    public List<MarcaMotoFacil> listarMarcaMotoFacil() {
        return socialManagerDao.listarMarcaMotoFacil();
    }

    public List<ModeloMotoFacil> listarModeloMotoFacil() {
        return socialManagerDao.listarModeloMotoFacil();
    }
}
