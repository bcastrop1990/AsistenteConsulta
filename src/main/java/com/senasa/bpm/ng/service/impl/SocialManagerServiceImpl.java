package com.senasa.bpm.ng.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senasa.bpm.ng.dao.EnfermedadDao;
import com.senasa.bpm.ng.dao.SocialManagerDao;
import com.senasa.bpm.ng.model.*;
import com.senasa.bpm.ng.model.bean.EnfermedadesBean;
import com.senasa.bpm.ng.model.response.EnfermedadResponse;
import com.senasa.bpm.ng.model.response.EnfermedadesPrimariasResponse;
import com.senasa.bpm.ng.service.EnfermedadService;
import com.senasa.bpm.ng.service.SocialManagerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
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
