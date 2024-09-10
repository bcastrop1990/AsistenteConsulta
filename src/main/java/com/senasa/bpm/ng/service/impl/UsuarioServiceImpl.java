package com.senasa.bpm.ng.service.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.senasa.bpm.ng.dao.EspecialidadDao;
import com.senasa.bpm.ng.dao.UsuarioDao;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.Usuario;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private final AmazonSimpleEmailService sesClient;
    @Autowired
    private UsuarioDao usuarioDao;

    public String crearUsuario(Usuario request) {

        return usuarioDao.crearUsuario(request);
    }

}
