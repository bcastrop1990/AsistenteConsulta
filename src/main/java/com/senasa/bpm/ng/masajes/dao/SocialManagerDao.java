package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.EstadoFinanciamiento;
import com.senasa.bpm.ng.masajes.model.MarcaMotoFacil;
import com.senasa.bpm.ng.masajes.model.ModeloMotoFacil;
import com.senasa.bpm.ng.masajes.model.TipoDeCompra;

import java.util.List;

public interface SocialManagerDao {

    List<EstadoFinanciamiento> listarEstadosFinanciamiento();
    List<TipoDeCompra> listarTipoCompra();
    List<MarcaMotoFacil> listarMarcaMotoFacil();
    List<ModeloMotoFacil> listarModeloMotoFacil();

}
