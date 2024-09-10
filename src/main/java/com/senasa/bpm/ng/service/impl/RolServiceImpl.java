package com.senasa.bpm.ng.service.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.senasa.bpm.ng.dao.RolDao;
import com.senasa.bpm.ng.dao.UsuarioDao;
import com.senasa.bpm.ng.model.Acceso;
import com.senasa.bpm.ng.model.Rol;
import com.senasa.bpm.ng.model.Usuario;
import com.senasa.bpm.ng.service.RolService;
import com.senasa.bpm.ng.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RolServiceImpl implements RolService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private final AmazonSimpleEmailService sesClient;
    @Autowired
    private RolDao rolDao;

    public String crear(Rol request) {

        return rolDao.crear(request);
    }
    @Override
    public List<Rol> listarRoles(Long empresa_id) {
        return rolDao.listarRoles(empresa_id);
    }
    @Override
    public List<Acceso> listarAccesos() {
        return rolDao.listarAccesos();
    }
}
