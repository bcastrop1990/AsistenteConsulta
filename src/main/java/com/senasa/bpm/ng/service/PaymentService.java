package com.senasa.bpm.ng.service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;

public interface PaymentService {

    String obtenerMerchantId();
    String obtenerSecretKey();
    int obtenerIdTransaccion(String nombre);
}
