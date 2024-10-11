package com.senasa.bpm.ng.masajes.service;

import com.senasa.bpm.ng.masajes.model.EstadoFinanciamiento;
import com.senasa.bpm.ng.masajes.model.MarcaMotoFacil;
import com.senasa.bpm.ng.masajes.model.ModeloMotoFacil;
import com.senasa.bpm.ng.masajes.model.TipoDeCompra;

import java.util.List;

public interface SocialManagerService {

    List<EstadoFinanciamiento> listarEstadosFinanciamiento();
    List<TipoDeCompra> listarTipoCompra();
    List<MarcaMotoFacil> listarMarcaMotoFacil();
    List<ModeloMotoFacil> listarModeloMotoFacil();


}
