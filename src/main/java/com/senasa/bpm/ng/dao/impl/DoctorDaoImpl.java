
package com.senasa.bpm.ng.dao.impl;
import com.senasa.bpm.ng.model.Medico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.senasa.bpm.ng.dao.DataDao;
import com.senasa.bpm.ng.dao.DoctorDao;
import com.senasa.bpm.ng.dao.rowmapper.*;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.bean.DoctorBean;
import com.senasa.bpm.ng.model.bean.ServiciosBean;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class DoctorDaoImpl implements DoctorDao {

    private RestTemplate restTemplate;
    private JdbcTemplate jdbcTemplate;


    @Override
    public Medico validarDoctor(DoctorRequest doctor) throws IOException {
        String baseUrl = "https://200.48.13.39/conoce_a_tu_medico/datos-colegiado-detallado.php";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("id", doctor.getCodigo());

        RestTemplate restTemplate = new RestTemplate();

        RequestCallback requestCallback = request -> {
            request.getHeaders().add("User-Agent", "Mozilla/5.0");
            request.getHeaders().add("Accept-Charset", "UTF-8");
        };

        ResponseExtractor<String> responseExtractor = response -> {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
                return bufferedReader.lines().collect(Collectors.joining("\n"));
            }
        };

        String html = restTemplate.execute(builder.toUriString(), org.springframework.http.HttpMethod.GET, requestCallback, responseExtractor);
        Document doc = Jsoup.parse(html);

        Element tabla = doc.select("table").get(0);
        Elements filas = tabla.select("tr");
        String apellidos = filas.get(1).select("td").get(1).text();
        String nombres = filas.get(1).select("td").get(2).text();
        Element tablaEstado = doc.select("table").get(1);
        String estado = normalizeString(tablaEstado.select("tr").get(0).select("td").text());
        Element tablaRegistros = doc.select("table").get(3); // Asumiendo que es la cuarta tabla en el documento
        Elements filasRegistro = tablaRegistros.select("tr");
        String especialidad = filasRegistro.get(1).select("td").get(0).text();
        Element tablaImagen = doc.select("table").get(2); // Asumiendo que la imagen está en la tercera tabla
        String urlImagen = tablaImagen.select("tr").get(1).select("td img").attr("src");

        String habiliNormalizado = normalizeString("HÁBIL");

        if (!apellidos.equalsIgnoreCase(doctor.getApellidos()) && !nombres.equalsIgnoreCase(doctor.getNombres())) {
            throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
        } else if (!nombres.equalsIgnoreCase(doctor.getNombres()) && apellidos.equalsIgnoreCase(doctor.getApellidos())) {
            throw new ApiValidateException("Los nombres ingresados no son validos.");
        } else if (nombres.equalsIgnoreCase(doctor.getNombres()) && !apellidos.equalsIgnoreCase(doctor.getApellidos())) {
            throw new ApiValidateException("Los apellidos ingresados no son validos.");
        }
        if (!estado.equalsIgnoreCase(habiliNormalizado)) {
            throw new ApiValidateException("Lo sentimos, pero usted no se encuentra en estado HÁBIL, comunicarse con 927365712.");
        }
        Medico medico = new Medico();
        medico.setEspecialidad(especialidad);
        medico.setImagen("https://200.48.13.39/conoce_a_tu_medico/"+urlImagen);

        return medico;
    }

    public Medico validarOdontologo(DoctorRequest request) {
        try {
            String apellidos = request.getApellidos();
            String nombres = request.getNombres();
            String codigo = request.getCodigo();
            String url = "https://sigacop.cop.org.pe/consultas_web/consulta_colegiado.asp";
            Connection.Response response = Jsoup.connect(url)
                    .data("TxtBusqueda", codigo)
                    .data("eje", "30")
                    .data("page", "1")
                    .method(Connection.Method.POST)
                    .execute();

            // Parsear la respuesta HTML
            Document doc = response.parse();
            Element table = doc.selectFirst("table.lista");

            // Comprobar si la tabla existe
            if (table == null) {
                throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
            }

            // Extraer información
            Element row = table.select("tr").get(1);
            if (row == null) {
                throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
            }
            Medico medico = new Medico();

            String nombreCompleto = row.select("td").get(2).text();
            String estado = row.select("td").get(4).text();
            String rawImageUrl = row.select("td img").attr("src"); // Extraer URL de la imagen
            String processedImageUrl = rawImageUrl.replaceFirst("^\\.\\.*\\\\", "").replace("\\", "/"); // Procesar la URL para formato web estándar

            // Validar nombre completo
            if (!nombreCompleto.contains(apellidos + " " + nombres)) {
                throw new ApiValidateException("Los nombres y apellidos deben coincidir con el COP "+codigo);
            }

            System.out.println("ESTE ES EL ESTADO :"+estado);
            // Validar estado
            if ("HABILITADO".equals(estado)) {
                medico.setEspecialidad("ODONTOLOGIA");
                medico.setImagen("https://sigacop.cop.org.pe/"+processedImageUrl);
                return medico;
            } else if ("NO HABILITADO".equals(estado)) {
                throw new ApiValidateException("Lo sentimos, pero usted no se encuentra en estado HÁBIL, comunicarse con 927365712.");
            } else {
                if(this.validarOdontologoLima(request)){
                    medico.setEspecialidad("ODONTOLOGIA");
                    medico.setImagen("https://sigacop.cop.org.pe/"+processedImageUrl);
                    return medico;
                }else{
                    throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
        }
    }




    public boolean validarOdontologoLima(DoctorRequest request) {
        try {
            String url = "https://buscador.col.org.pe/recursos/recursos_json.php";

            String requestBody = String.format("{\"recursos\":{\"id1\":1,\"tipo_busqueda\":\"1\",\"cop\":\"%s\",\"nombres\":null,\"apellidos\":null}}", request.getCodigo());

            Connection.Response response = Jsoup.connect(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data("data", requestBody)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();

            JSONObject jsonResponse = new JSONObject(response.body());
            int cargado = jsonResponse.getInt("cargado");

            if (cargado == 1) {
                JSONArray registros = jsonResponse.getJSONArray("registros");
                if (registros.length() > 0) {
                    JSONObject registro = registros.getJSONObject(0);
                    String estado = registro.optString("estado", "").trim();


                    if (!estado.contains("HABILITADO")) {
                        throw new ApiValidateException("Lo sentimos, pero usted no se encuentra en estado HÁBIL, comunicarse con 927365712.");
                    }

                    return true;
                } else {
                    throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
                }
            } else {
                throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiValidateException("Error de conexión");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }





    private String normalizeString(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }



}
