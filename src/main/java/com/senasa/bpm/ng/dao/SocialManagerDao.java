package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.*;
import com.senasa.bpm.ng.model.bean.EnfermedadesBean;

import java.util.Date;
import java.util.List;

public interface SocialManagerDao {

    List<EstadoFinanciamiento> listarEstadosFinanciamiento();
    List<TipoDeCompra> listarTipoCompra();
    List<MarcaMotoFacil> listarMarcaMotoFacil();
    List<ModeloMotoFacil> listarModeloMotoFacil();

}
