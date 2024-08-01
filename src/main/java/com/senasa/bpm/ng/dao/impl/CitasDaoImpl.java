
package com.senasa.bpm.ng.dao.impl;
/*
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.services.calendar.Calendar;*/
import lombok.AllArgsConstructor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.senasa.bpm.ng.dao.CitasDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
@AllArgsConstructor
@Service
@Slf4j
public class CitasDaoImpl implements CitasDao {

    private JdbcTemplate jdbcTemplate;

    private static final String APPLICATION_NAME = "Google Calendar API Java Integration";
    //private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/calendar");
//    private static final String CREDENTIALS_FILE_PATH = "/credencialrodrigo.json"; // Ruta relativa desde la carpeta de recursos
//    private static final String TOKENS_DIRECTORY_PATH = "./tokens/token.json";
//
//    @Override
//    public Calendar getCalendarService() throws Exception {
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        Credential credential = this.getCredentials(HTTP_TRANSPORT);
//        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//
//    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
//        // Cargar el archivo como recurso
//        InputStream in = CitasDaoImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//    @Override
//    public String insertarDisponibilidad(String email, String hora_inicio, String hora_fin) {
//        String horaStr = hora_inicio.substring(0, 2);
//        int hora_inicio_int = Integer.parseInt(horaStr);
//        String horaStr2 = hora_fin.substring(0, 2);
//        int hora_fin_int = Integer.parseInt(horaStr2);
//
//        String sql = "UPDATE disponibilidad SET hora_inicio = ?, hora_fin = ? WHERE email = ?";
//        return String.valueOf(jdbcTemplate.update(sql, hora_inicio_int, hora_fin_int, email));
//    }
    @Override
    public int[] obtenerHorasDisponibilidad(String email) {
        String sql = "SELECT hora_inicio, hora_fin FROM disponibilidad WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> new int[]{
                rs.getInt("hora_inicio"),
                rs.getInt("hora_fin")
        });
    }
    public int obtenerIdTransaccion(String nombre) {
        String sql = "INSERT INTO comprobante_pago (numero_transaccion, estado) VALUES (?, 'Correcto')";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }


}
