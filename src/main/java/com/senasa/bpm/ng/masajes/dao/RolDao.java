package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.Acceso;
import com.senasa.bpm.ng.masajes.model.Rol;

import java.util.List;

public interface MasajesRolDao {
    String crear(Rol request);
    List<Rol> listarRoles(Long empresa_id);
    List<Acceso> listarAccesos();
}
