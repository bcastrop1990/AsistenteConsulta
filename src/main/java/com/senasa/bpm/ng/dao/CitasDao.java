package com.senasa.bpm.ng.dao;

import java.util.List;

public interface CitasDao {
//    Calendar getCalendarService() throws Exception;
////  Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT);
//    String insertarDisponibilidad(String email, String hora_inicio, String hora_fin);
    int[] obtenerHorasDisponibilidad(String email);
    int obtenerIdTransaccion(String nombre);
}
