
package com.senasa.bpm.ng.dao.impl;
import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedResponse;
import com.senasa.bpm.ng.model.response.DoctorResponse;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class DoctorDaoImpl implements DoctorDao {

    private RestTemplate restTemplate;
    private JdbcTemplate jdbcTemplate;

/*
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

        if (!apellidos.equalsIgnoreCase(doctor.getApellido()) && !nombres.equalsIgnoreCase(doctor.getNombre())) {
            throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
        } else if (!nombres.equalsIgnoreCase(doctor.getNombre()) && apellidos.equalsIgnoreCase(doctor.getApellido())) {
            throw new ApiValidateException("Los nombres ingresados no son validos.");
        } else if (nombres.equalsIgnoreCase(doctor.getNombre()) && !apellidos.equalsIgnoreCase(doctor.getApellido())) {
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
            String apellidos = request.getApellido();
            String nombres = request.getNombre();
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
*/

    public  List<DoctorCubaMedResponse> listarDoctor(Long idEspecialidad, String nombre){

        StringBuilder sql = new StringBuilder("SELECT * FROM doctores WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }

        if (idEspecialidad != 0) {
            sql.append(" AND idEspecialidad = ?");
            params.add(idEspecialidad);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs) -> {
            List<DoctorCubaMedResponse> doctores = new ArrayList<>();
            while (rs.next()) {
                DoctorCubaMedResponse doctor = DoctorCubaMedResponse.builder()
                        .idDoctor(rs.getLong("id"))
                        .nombres(rs.getString("nombre"))
                        .apellidos(rs.getString("apellido"))
                        .numeroCelular(rs.getString("celular"))
                        .email(rs.getString("email"))
                        .colorIndentificador(rs.getString("colorIdentificador"))
                        .id_especialidad(rs.getLong("idEspecialidad"))
                        .imagen(rs.getString("imagen"))
                        .build();
                doctores.add(doctor);
            }
            return doctores;
        });
    }

    @Override
    public void configurarDisponibilidadDoctor(DoctorDisponibilidadRequest request) {
        // Obtener el idDoctor a partir del correo
        Long idDoctor = obtenerIdDoctorPorCorreo(request.getEmail());

        // Consulta para verificar si ya existe un registro para el idDoctor
        String checkSql = "SELECT COUNT(*) FROM doctor_disponibilidad WHERE idDoctor = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{idDoctor}, Integer.class);

        if (count != null && count > 0) {
            // Si ya existe, actualizamos el registro
            String updateSql = "UPDATE doctor_disponibilidad SET fecha_inicio = ?, fecha_fin = ?, dias_semana = ?, color = ?, " +
                    "hora_inicio_lunes = ?, hora_fin_lunes = ?, " +
                    "hora_inicio_martes = ?, hora_fin_martes = ?, " +
                    "hora_inicio_miercoles = ?, hora_fin_miercoles = ?, " +
                    "hora_inicio_jueves = ?, hora_fin_jueves = ?, " +
                    "hora_inicio_viernes = ?, hora_fin_viernes = ?, " +
                    "hora_inicio_sabado = ?, hora_fin_sabado = ?, " +
                    "hora_inicio_domingo = ?, hora_fin_domingo = ? " +
                    "WHERE idDoctor = ?";
            jdbcTemplate.update(updateSql,
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    String.join(",", request.getDiasSemana()),
                    request.getColor(),
                    request.getHoraInicioLunes(), request.getHoraFinLunes(),
                    request.getHoraInicioMartes(), request.getHoraFinMartes(),
                    request.getHoraInicioMiercoles(), request.getHoraFinMiercoles(),
                    request.getHoraInicioJueves(), request.getHoraFinJueves(),
                    request.getHoraInicioViernes(), request.getHoraFinViernes(),
                    request.getHoraInicioSabado(), request.getHoraFinSabado(),
                    request.getHoraInicioDomingo(), request.getHoraFinDomingo(),
                    idDoctor);
        } else {
            // Si no existe, insertamos un nuevo registro
            String insertSql = "INSERT INTO doctor_disponibilidad (idDoctor, fecha_inicio, fecha_fin, dias_semana, color, " +
                    "hora_inicio_lunes, hora_fin_lunes, " +
                    "hora_inicio_martes, hora_fin_martes, " +
                    "hora_inicio_miercoles, hora_fin_miercoles, " +
                    "hora_inicio_jueves, hora_fin_jueves, " +
                    "hora_inicio_viernes, hora_fin_viernes, " +
                    "hora_inicio_sabado, hora_fin_sabado, " +
                    "hora_inicio_domingo, hora_fin_domingo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    idDoctor,
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    String.join(",", request.getDiasSemana()),
                    request.getColor(),
                    request.getHoraInicioLunes(), request.getHoraFinLunes(),
                    request.getHoraInicioMartes(), request.getHoraFinMartes(),
                    request.getHoraInicioMiercoles(), request.getHoraFinMiercoles(),
                    request.getHoraInicioJueves(), request.getHoraFinJueves(),
                    request.getHoraInicioViernes(), request.getHoraFinViernes(),
                    request.getHoraInicioSabado(), request.getHoraFinSabado(),
                    request.getHoraInicioDomingo(), request.getHoraFinDomingo());
        }
    }




    public Long obtenerIdDoctorPorCorreo(String email) {
        String sql = "SELECT id FROM doctores WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, Long.class);
    }


    public DoctorCubaMedDisponibilidadResponse obtenerDisponibilidadPorCorreo(String email) {
        // Obtener el idDoctor a partir del correo
        Long idDoctor = obtenerIdDoctorPorCorreo(email);

        // Consulta para obtener la disponibilidad del doctor
        String sql = "SELECT * FROM doctor_disponibilidad WHERE idDoctor = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{idDoctor}, (rs, rowNum) ->
                    DoctorCubaMedDisponibilidadResponse.builder()
                            .idDoctor(idDoctor)
                            .fechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null)
                            .fechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null)
                            .diasSemana(rs.getString("dias_semana") != null ? Arrays.asList(rs.getString("dias_semana").split(",")) : null)
                            .color(rs.getString("color"))
                            .horaInicioLunes(rs.getTime("hora_inicio_lunes") != null ? rs.getTime("hora_inicio_lunes").toLocalTime() : null)
                            .horaFinLunes(rs.getTime("hora_fin_lunes") != null ? rs.getTime("hora_fin_lunes").toLocalTime() : null)
                            .horaInicioMartes(rs.getTime("hora_inicio_martes") != null ? rs.getTime("hora_inicio_martes").toLocalTime() : null)
                            .horaFinMartes(rs.getTime("hora_fin_martes") != null ? rs.getTime("hora_fin_martes").toLocalTime() : null)
                            .horaInicioMiercoles(rs.getTime("hora_inicio_miercoles") != null ? rs.getTime("hora_inicio_miercoles").toLocalTime() : null)
                            .horaFinMiercoles(rs.getTime("hora_fin_miercoles") != null ? rs.getTime("hora_fin_miercoles").toLocalTime() : null)
                            .horaInicioJueves(rs.getTime("hora_inicio_jueves") != null ? rs.getTime("hora_inicio_jueves").toLocalTime() : null)
                            .horaFinJueves(rs.getTime("hora_fin_jueves") != null ? rs.getTime("hora_fin_jueves").toLocalTime() : null)
                            .horaInicioViernes(rs.getTime("hora_inicio_viernes") != null ? rs.getTime("hora_inicio_viernes").toLocalTime() : null)
                            .horaFinViernes(rs.getTime("hora_fin_viernes") != null ? rs.getTime("hora_fin_viernes").toLocalTime() : null)
                            .horaInicioSabado(rs.getTime("hora_inicio_sabado") != null ? rs.getTime("hora_inicio_sabado").toLocalTime() : null)
                            .horaFinSabado(rs.getTime("hora_fin_sabado") != null ? rs.getTime("hora_fin_sabado").toLocalTime() : null)
                            .horaInicioDomingo(rs.getTime("hora_inicio_domingo") != null ? rs.getTime("hora_inicio_domingo").toLocalTime() : null)
                            .horaFinDomingo(rs.getTime("hora_fin_domingo") != null ? rs.getTime("hora_fin_domingo").toLocalTime() : null)
                            .build()
            );
        } catch (EmptyResultDataAccessException e) {
            // Si no se encuentra un registro, devolver un objeto con todos los valores nulos
            return DoctorCubaMedDisponibilidadResponse.builder()
                    .idDoctor(idDoctor)
                    .fechaInicio(null)
                    .fechaFin(null)
                    .diasSemana(null)
                    .color(null)
                    .horaInicioLunes(null)
                    .horaFinLunes(null)
                    .horaInicioMartes(null)
                    .horaFinMartes(null)
                    .horaInicioMiercoles(null)
                    .horaFinMiercoles(null)
                    .horaInicioJueves(null)
                    .horaFinJueves(null)
                    .horaInicioViernes(null)
                    .horaFinViernes(null)
                    .horaInicioSabado(null)
                    .horaFinSabado(null)
                    .horaInicioDomingo(null)
                    .horaFinDomingo(null)
                    .build();
        }
    }

    // Guardar nuevo doctor
    public DoctorResponse guardarDoctor(DoctorRequest doctorRequest) {
        String sql = "INSERT INTO doctores (nombre, apellido, celular, email, colorIdentificador, idEspecialidad, imagen, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                doctorRequest.getNombre(),
                doctorRequest.getApellido(),
                doctorRequest.getCelular(),
                doctorRequest.getEmail(),
                doctorRequest.getColorIdentificador(),
                doctorRequest.getIdEspecialidad(),
                doctorRequest.getImagen(),
                doctorRequest.getEstado());

        Long idDoctor = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return DoctorResponse.builder()
                .id(idDoctor)
                .nombre(doctorRequest.getNombre())
                .apellido(doctorRequest.getApellido())
                .celular(doctorRequest.getCelular())
                .email(doctorRequest.getEmail())
                .colorIdentificador(doctorRequest.getColorIdentificador())
                .idEspecialidad(doctorRequest.getIdEspecialidad())
                .imagen(doctorRequest.getImagen())
                .estado(doctorRequest.getEstado())
                .build();
    }

    // Editar un doctor existente
    public DoctorResponse editarDoctor(Long id, DoctorRequest doctorRequest) {
        String sql = "UPDATE doctores SET nombre = ?, apellido = ?, celular = ?, email = ?, colorIdentificador = ?, idEspecialidad = ?, imagen = ?, estado = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                doctorRequest.getNombre(),
                doctorRequest.getApellido(),
                doctorRequest.getCelular(),
                doctorRequest.getEmail(),
                doctorRequest.getColorIdentificador(),
                doctorRequest.getIdEspecialidad(),
                doctorRequest.getImagen(),
                doctorRequest.getEstado(),
                id);

        return DoctorResponse.builder()
                .id(id)
                .nombre(doctorRequest.getNombre())
                .apellido(doctorRequest.getApellido())
                .celular(doctorRequest.getCelular())
                .email(doctorRequest.getEmail())
                .colorIdentificador(doctorRequest.getColorIdentificador())
                .idEspecialidad(doctorRequest.getIdEspecialidad())
                .imagen(doctorRequest.getImagen())
                .estado(doctorRequest.getEstado())
                .build();
    }

    // Listar todos los doctores
    public List<DoctorResponse> listarTodosLosDoctores() {
        String sql = "SELECT id, nombre, apellido, celular, email, colorIdentificador, idEspecialidad, imagen, estado FROM doctores";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                DoctorResponse.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .celular(rs.getString("celular"))
                        .email(rs.getString("email"))
                        .colorIdentificador(rs.getString("colorIdentificador"))
                        .idEspecialidad(rs.getLong("idEspecialidad"))
                        .imagen(rs.getString("imagen"))
                        .estado(rs.getInt("estado"))
                        .build()
        );
    }

    public DoctorResponse alternarEstadoDoctor(int id) {
        String sqlSelect = "SELECT estado FROM doctores WHERE id = ?";
        Integer estadoActual = jdbcTemplate.queryForObject(sqlSelect, new Object[]{id}, Integer.class);

        if (estadoActual == null) {
            throw new RuntimeException("No se encontró el doctor con ID: " + id);
        }

        // Cambiar el estado entre 1 y 0
        int nuevoEstado = (estadoActual == 1) ? 0 : 1;

        String sqlUpdate = "UPDATE doctores SET estado = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdate, nuevoEstado, id);

        // Obtener los datos actualizados del doctor
        String sqlGetDoctor = "SELECT * FROM doctores WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlGetDoctor, new Object[]{id}, (rs, rowNum) ->
                DoctorResponse.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .celular(rs.getString("celular"))
                        .email(rs.getString("email"))
                        .colorIdentificador(rs.getString("colorIdentificador"))
                        .idEspecialidad(rs.getLong("idEspecialidad"))
                        .imagen(rs.getString("imagen"))
                        .estado(rs.getInt("estado"))  // Devuelve el nuevo estado (0 o 1)
                        .build()
        );
    }


}

