package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.Acceso;
import com.senasa.bpm.ng.model.Rol;
import com.senasa.bpm.ng.model.Usuario;

import java.util.List;

public interface RolService {

    String crear(Rol request);
    List<Rol> listarRoles(Long empresa_id);
    List<Acceso> listarAccesos();

}
