package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.Acceso;
import com.senasa.bpm.ng.model.Rol;
import com.senasa.bpm.ng.model.bean.DoctorBean;
import com.senasa.bpm.ng.model.bean.ServiciosBean;

import java.util.List;

public interface RolDao {
    String crear(Rol request);
    List<Rol> listarRoles(Long empresa_id);
    List<Acceso> listarAccesos();
}
