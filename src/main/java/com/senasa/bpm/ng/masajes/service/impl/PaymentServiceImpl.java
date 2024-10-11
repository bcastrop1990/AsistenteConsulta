package com.senasa.bpm.ng.masajes.service.impl;

import com.senasa.bpm.ng.masajes.dao.CitasDao;
import com.senasa.bpm.ng.masajes.dao.TokenDao;
import com.senasa.bpm.ng.masajes.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TokenDao tokenDao;
    private CitasDao citasDao;

    @Override
    public String obtenerMerchantId(){
        return tokenDao.obtenerMerchantId();
    }
    @Override
    public String obtenerSecretKey(){
        return tokenDao.obtenerSecretKey();
    }

    @Override
    public int obtenerIdTransaccion(String nombre){
        return citasDao.obtenerIdTransaccion(nombre);
    }
}
