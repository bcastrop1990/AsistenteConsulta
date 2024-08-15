package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.*;
import com.senasa.bpm.ng.model.response.EnfermedadResponse;
import com.senasa.bpm.ng.model.response.EnfermedadesPrimariasResponse;

import java.util.Date;
import java.util.List;

public interface SocialManagerService {

    List<EstadoFinanciamiento> listarEstadosFinanciamiento();
    List<TipoDeCompra> listarTipoCompra();
    List<MarcaMotoFacil> listarMarcaMotoFacil();
    List<ModeloMotoFacil> listarModeloMotoFacil();


}
