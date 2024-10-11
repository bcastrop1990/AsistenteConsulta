package com.senasa.bpm.ng.masajes.service;


public interface PaymentService {

    String obtenerMerchantId();
    String obtenerSecretKey();
    int obtenerIdTransaccion(String nombre);
}
