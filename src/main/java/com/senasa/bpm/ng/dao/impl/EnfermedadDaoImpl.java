
package com.senasa.bpm.ng.dao.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senasa.bpm.ng.dao.EnfermedadDao;
import com.senasa.bpm.ng.dao.rowmapper.*;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.*;
import com.senasa.bpm.ng.model.bean.EnfermedadesBean;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.EnfermedadResponse;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class EnfermedadDaoImpl implements EnfermedadDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<EnfermedadesBean> listarEnfermedadesPrimarias() {
        String sql = "SELECT * FROM enfermedad_pr";
        try {
            return jdbcTemplate.query(sql, new EnfermedadesPrimariasRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // Retornar una lista vacía en lugar de Optional.empty() cuando no hay resultados
            return Collections.emptyList();}
    }
    @Override
    public List<Producto> listarProducto() {
        String sql = "select producto.*,  marca.nombre as nombre_marca from producto inner join marca on producto.marca = marca.id where producto.estado = 0";
        try {
            return jdbcTemplate.query(sql, new ProductoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // Retornar una lista vacía en lugar de Optional.empty() cuando no hay resultados
            return Collections.emptyList();}
    }
    @Override
    public List<EnfermedadesBean> listarPorEnfrPr(Long id)  {
        String sql = "SELECT * FROM enfermedad where id_EnfPr = ?";
        try {
            return jdbcTemplate.query(sql, new EnfermedadesRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<Producto> listarProductoPorMarca(Long id)  {
        String sql = "select producto.*,  marca.nombre as nombre_marca from producto inner join marca on producto.marca = marca.id where producto.marca = ? and estado = 0";
        try {
            return jdbcTemplate.query(sql, new ProductoRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
    @Override
    public List<Marca> listarMarcas() {
        String sql = "SELECT * FROM marca";
        try {
            return jdbcTemplate.query(sql, new MarcaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public String agregarProd(Producto request) {
        String codigo = request.getCodigo();
        String sqlCount = "SELECT COUNT(*) FROM producto WHERE codigo = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, codigo) >= 1){
            throw new ApiValidateException("El codigo: "+codigo+" ya se encuentra registrado.");
        }
        String sql = "INSERT INTO producto (nombre, marca, descrp, precio, imagen, codigo, estado, stock) VALUES (?, ?, ?, ?, ?, ?, 0, ?)";
        return String.valueOf(jdbcTemplate.update(sql, request.getNombre(), request.getMarca(), request.getDesc(),
                request.getPrecio(), request.getImagen(),request.getCodigo(), request.getStock()));
    }


    @Override
    public Datos obtenerDatos() {
        Datos datos = new Datos();

        String sqlAprobados = "SELECT COUNT(*) FROM clientes_moto_facil WHERE estado = 'Aprobado'";
        String sqlDesaprobados = "SELECT COUNT(*) FROM clientes_moto_facil WHERE estado = 'Desaprobado'";
        String sqlMarcaMasSolicitada = "SELECT marca " +
                "FROM clientes_moto_facil " +
                "WHERE LOWER(marca) <> 'pendiente' " +
                "GROUP BY marca " +
                "ORDER BY COUNT(*) DESC " +
                "LIMIT 1";

        try {
            String marcaMasSolicitada = jdbcTemplate.queryForObject(sqlMarcaMasSolicitada, String.class);
            datos.setMarcaMasSolicitada(marcaMasSolicitada);
        } catch (EmptyResultDataAccessException e) {
            datos.setMarcaMasSolicitada("No disponible");
        }



        String sqlFinanciamiento = "SELECT COUNT(*) FROM clientes_moto_facil WHERE tipo_compra = 'financiamiento'";
        String sqlAlContado = "SELECT COUNT(*) FROM clientes_moto_facil WHERE tipo_compra = 'al contado'";
        String sqlTarjetaCredito = "SELECT COUNT(*) FROM clientes_moto_facil WHERE tipo_compra = 'tarjeta de crédito'";


        try {
            datos.setCantidadAprobados(jdbcTemplate.queryForObject(sqlAprobados, Integer.class));
            datos.setCantidadDesaprobados(jdbcTemplate.queryForObject(sqlDesaprobados, Integer.class));
            datos.setMarcaMasSolicitada(jdbcTemplate.queryForObject(sqlMarcaMasSolicitada, String.class));
            datos.setCantidadFinanciamiento(jdbcTemplate.queryForObject(sqlFinanciamiento, Integer.class));
            datos.setCantidadAlContado(jdbcTemplate.queryForObject(sqlAlContado, Integer.class));
            datos.setCantidadTarjetaCredito(jdbcTemplate.queryForObject(sqlTarjetaCredito, Integer.class));
        } catch (EmptyResultDataAccessException e) {
            return new Datos();
        }

        return datos;
    }
    @Override
    public String cambiarEstado(Activo request) {
        String sql = "UPDATE clientes_moto_facil SET activo = ? WHERE id = ?";
        String.valueOf(jdbcTemplate.update(sql, request.getActivo(), request.getId()));
        return "Se cambio estado exitosamente.";
    }

    @Override
    public DatosImportantes listarDatosImportantes() {
        DatosImportantes datos = new DatosImportantes();

        // Total de filas
        String sqlTotalFilas = "SELECT COUNT(*) FROM clientes_moto_facil";
        int totalFilas = jdbcTemplate.queryForObject(sqlTotalFilas, Integer.class);
        datos.setTotalFilas(totalFilas);

        // Filas por modelo
        String sqlFilasPorModelo = "SELECT modelo, COUNT(*) as cantidad FROM clientes_moto_facil GROUP BY modelo";
        Map<String, Long> filasPorModelo = jdbcTemplate.query(sqlFilasPorModelo, (rs, rowNum) ->
                new AbstractMap.SimpleEntry<>(rs.getString("modelo"), rs.getLong("cantidad"))
        ).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        datos.setFilasPorModelo(filasPorModelo);

        // Filas por marca
        String sqlFilasPorMarca = "SELECT marca, COUNT(*) as cantidad FROM clientes_moto_facil GROUP BY marca";
        Map<String, Long> filasPorMarca = jdbcTemplate.query(sqlFilasPorMarca, (rs, rowNum) ->
                new AbstractMap.SimpleEntry<>(rs.getString("marca"), rs.getLong("cantidad"))
        ).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        datos.setFilasPorMarca(filasPorMarca);

        // Estados aprobados y desaprobados por semana
        String sqlEstadosPorSemana = "SELECT estado, DATE_FORMAT(fecha, '%X-%V') as semana, COUNT(*) as cantidad FROM clientes_moto_facil WHERE estado IN ('Aprobado', 'Desaprobado') GROUP BY semana, estado";
        List<Map<String, Object>> estadosPorSemana = jdbcTemplate.queryForList(sqlEstadosPorSemana);
        Map<String, Integer> aprobadosPorSemana = new HashMap<>();
        Map<String, Integer> desaprobadosPorSemana = new HashMap<>();
        estadosPorSemana.forEach(map -> {
            String estado = (String) map.get("estado");
            String semana = (String) map.get("semana");
            Number cantidad = (Number) map.get("cantidad"); // Usa Number para ser seguro
            if ("Aprobado".equals(estado)) {
                aprobadosPorSemana.put(semana, cantidad.intValue());
            } else if ("Desaprobado".equals(estado)) {
                desaprobadosPorSemana.put(semana, cantidad.intValue());
            }
        });
        datos.setAprobadosPorSemana(aprobadosPorSemana);
        datos.setDesaprobadosPorSemana(desaprobadosPorSemana);

        return datos;
    }



    @Override
    public String agregarCategoria(Categoria request) {
        String codigo = request.getNombre();
        String sqlCount = "SELECT COUNT(*) FROM categoria WHERE nombre = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, codigo) >= 1){
            throw new ApiValidateException("El nombre: "+request.getNombre()+" ya se encuentra registrado.");
        }

        String sql = "INSERT INTO categoria (nombre, estado) VALUES (?, 0)";
        return String.valueOf(jdbcTemplate.update(sql, request.getNombre()));
    }

    @Override
    public void eliminarProducto(Long codigo) {
        String sqlCount = "SELECT COUNT(*) FROM producto WHERE id = ?";
        String sqlCount2 = "SELECT COUNT(*) FROM producto WHERE id = ? and estado = 1";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, codigo) == 0){
            throw new ApiValidateException("El producto con el codigo: "+codigo+" no existe.");
        } else if (jdbcTemplate.queryForObject(sqlCount2, Integer.class, codigo) >= 1){
            throw new ApiValidateException("El producto con el codigo: "+codigo+" ya fue eliminado.");
        }
        String sql = "UPDATE producto SET estado = 1 WHERE id = ?";
        String.valueOf(jdbcTemplate.update(sql, codigo));
    }
    @Override
    public List<Cliente> listarClientes() {
        String sql = "SELECT clientes.*, categoria.nombre as nombre_categoria FROM clientes inner join categoria on clientes.categoria = categoria.id where clientes.estado = 0";
        try {
            return jdbcTemplate.query(sql, new ClienteRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // Retornar una lista vacía en lugar de Optional.empty() cuando no hay resultados
            return Collections.emptyList();}
    }

    @Override
    public Page<Cliente> listarClientesMotoFacil(String celular, String nombre_completo, String ubicacion, String cuota_inicial, String modelo, String marca, String email, String dni,  String tipo_compra,String estado, LocalDate fechaDesde, LocalDate fechaHasta, String activo, int page) {
        int limit = 10;
        int offset = (page - 1) * limit;

        StringBuilder sql = new StringBuilder("SELECT * FROM clientes_moto_facil WHERE 1 = 1");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (celular != null && !celular.isEmpty()) {
            sql.append(" AND celular = :celular");
            params.addValue("celular", celular);
        }
        if (nombre_completo != null && !nombre_completo.isEmpty()) {
            sql.append(" AND nombre_completo = :nombre_completo");
            params.addValue("nombre_completo", nombre_completo);
        }
        if (ubicacion != null && !ubicacion.isEmpty()) {
            sql.append(" AND ubicacion = :ubicacion");
            params.addValue("ubicacion", ubicacion);
        }
        if (tipo_compra != null && !tipo_compra.isEmpty()) {
            sql.append(" AND tipo_compra = :tipo_compra");
            params.addValue("tipo_compra", tipo_compra);
        }
        if (cuota_inicial != null && !cuota_inicial.isEmpty()) {
            sql.append(" AND cuota_inicial = :cuota_inicial");
            params.addValue("cuota_inicial", cuota_inicial);
        }
        if (modelo != null && !modelo.isEmpty()) {
            sql.append(" AND modelo = :modelo");
            params.addValue("modelo", modelo);
        }
        if (marca != null && !marca.isEmpty()) {
            sql.append(" AND marca = :marca");
            params.addValue("marca", marca);
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE :email");
            params.addValue("email", email + "%"); // Agrega '%' al final para buscar correos que comiencen con el texto dado
        }
        if (dni != null && !dni.isEmpty()) {
            sql.append(" AND dni = :dni");
            params.addValue("dni", dni);
        }
        if (estado != null && !estado.isEmpty()) {
            sql.append(" AND estado = :estado");
            params.addValue("estado", estado);
        }

        if (fechaDesde != null) {
            sql.append(" AND fecha >= :fechaDesde");
            params.addValue("fechaDesde", fechaDesde);
        }
        if (fechaHasta != null) {
            sql.append(" AND fecha <= :fechaHasta");
            params.addValue("fechaHasta", fechaHasta);
        }

        sql.append(" LIMIT :limit OFFSET :offset");
        params.addValue("limit", limit);
        params.addValue("offset", offset);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        List<Cliente> clientes = namedParameterJdbcTemplate.query(sql.toString(), params, new ClienteRowMapper());

        // Obtener el total de registros
        String countSql = "SELECT COUNT(*) FROM clientes_moto_facil WHERE 1 = 1";
        int total = namedParameterJdbcTemplate.queryForObject(countSql, params, Integer.class);

        return new PageImpl<>(clientes, PageRequest.of(page, limit), total);
    }



    @Override
    public void eliminarCliente(Long codigo) {
        String sqlCount = "SELECT COUNT(*) FROM clientes WHERE id = ?";
        String sqlCount2 = "SELECT COUNT(*) FROM clientes WHERE id = ? and estado = 1";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, codigo) == 0){
            throw new ApiValidateException("El cliente con el codigo: "+codigo+" no existe.");
        } else if (jdbcTemplate.queryForObject(sqlCount2, Integer.class, codigo) >= 1){
            throw new ApiValidateException("El cliente con el codigo: "+codigo+" ya fue eliminado.");
        }
        String sql = "UPDATE clientes SET estado = 1 WHERE id = ?";
        String.valueOf(jdbcTemplate.update(sql, codigo));
    }
    @Override
    public void eliminarCategoria(Long codigo) {
        String sqlCount = "SELECT COUNT(*) FROM categoria WHERE id = ?";
        String sqlCount2 = "SELECT COUNT(*) FROM categoria WHERE id = ? and estado = 1";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, codigo) == 0){
            throw new ApiValidateException("La categoria con el codigo: "+codigo+" no existe.");
        } else if (jdbcTemplate.queryForObject(sqlCount2, Integer.class, codigo) >= 1){
            throw new ApiValidateException("La categoria con el codigo: "+codigo+" ya fue eliminado.");
        }
        String sql = "UPDATE categoria SET estado = 1 WHERE id = ?";
        String.valueOf(jdbcTemplate.update(sql, codigo));
    }
    @Override
    public List<Venta> listarPorFecha(Date desde, Date hasta) {
        String sql = "SELECT * FROM ventas WHERE fecha BETWEEN ? AND ?";
        try {
            return jdbcTemplate.query(sql, new VentaRowMapper(), desde, hasta);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Venta> listarTodo() {
        String sql = "SELECT * FROM ventas";
        try {
            return jdbcTemplate.query(sql, new VentaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Venta> listarPorMes(int mes, int año) {
        String sql = "SELECT * FROM ventas WHERE MONTH(fecha) = ? AND YEAR(fecha) = ?";
        try {
            return jdbcTemplate.query(sql, new VentaRowMapper(), mes, año);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Venta> listarPorAño(int año) {
        String sql = "SELECT * FROM ventas WHERE YEAR(fecha) = ?";
        try {
            return jdbcTemplate.query(sql, new VentaRowMapper(), año);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Venta> listarPorDia(int dia, int mes, int año) {
        String sql = "SELECT * FROM ventas WHERE DAY(fecha) = ? AND MONTH(fecha) = ? AND YEAR(fecha) = ?";
        try {
            return jdbcTemplate.query(sql, new VentaRowMapper(), dia, mes, año);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void guardar(String celular, String nombres, String apellidos, String estado) {
        String sql = "UPDATE clientes_prueba SET nombres = ?, apellidos = ?, estado = ? WHERE celular = ?";
        String.valueOf(jdbcTemplate.update(sql, nombres, apellidos, estado, celular));
    }

    @Override
    public void guardarInfoMotoFacil(String celular, String nombre_completo, String ubicacion, String tipo_compra, String cuota_inicial, String modelo, String marca, String email) {
        //PASO 1: insertar en MODELO_MOTO_FACIL
        String sqlCheckModelo = "SELECT COUNT(*) FROM MODELO_MOTO_FACIL WHERE descripcion = ?";
        int countModelo = jdbcTemplate.queryForObject(sqlCheckModelo, new Object[]{modelo}, Integer.class);
        if (countModelo == 0) {
            String sqlInsert = "INSERT INTO MODELO_MOTO_FACIL (descripcion) VALUES (?)";
            jdbcTemplate.update(sqlInsert, modelo);
        }
        //PASO 2: insertar en MARCA_MOTO_FACIL
        String sqlCheckMarca = "SELECT COUNT(*) FROM MARCA_MOTO_FACIL WHERE descripcion = ?";
        int countMarca = jdbcTemplate.queryForObject(sqlCheckMarca, new Object[]{modelo}, Integer.class);
        if (countMarca == 0) {
            String sqlInsert = "INSERT INTO MARCA_MOTO_FACIL (descripcion) VALUES (?)";
            jdbcTemplate.update(sqlInsert, modelo);
        }


        String sql = "UPDATE clientes_moto_facil SET nombre_completo = ?, ubicacion = ?, tipo_compra = ?, cuota_inicial = ?, modelo = ?, marca = ?, email=? WHERE celular = ?";
        jdbcTemplate.update(sql, nombre_completo, ubicacion, tipo_compra, cuota_inicial, modelo, marca, email, celular);

    }


    @Override
    public void guardarPrimero(String celular) {
        String sqlCount = "SELECT COUNT(*) FROM clientes_moto_facil WHERE celular = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, celular) >= 1){
        }else{
            String sql = "INSERT INTO clientes_moto_facil (celular) VALUES (?)";
            String.valueOf(jdbcTemplate.update(sql, celular));
        }
    }
    @Override
    public String getAnswer(String prompt) {

        String sqlQuery = "SELECT * FROM clientes_moto_facil";
        List<String> clientesList = jdbcTemplate.query(sqlQuery, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                // Formatear cada fila en un string personalizado
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String cliente = String.format(
                        "{ID: %d, Nombre: \"%s\", Estado: \"%s\", DNI: \"%s\", Tipo de Compra: \"%s\", Cuota Inicial: \"%s\", Modelo: \"%s\", Marca: \"%s\", Celular: \"%s\", Ubicacion: \"%s\", Fecha: \"%s\"}",
                        rs.getLong("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("estado"),
                        rs.getString("dni"),
                        rs.getString("tipo_compra"),
                        rs.getString("cuota_inicial"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getString("celular"),
                        rs.getString("ubicacion"),
                        sdf.format(rs.getDate("fecha"))
                );
                return cliente;
            }
        });

        String clientesConcatenados = String.join(", ", clientesList);

        ZonedDateTime limaTime = ZonedDateTime.now(ZoneId.of("America/Lima"));

        // Formatear la fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");

        // Mostrar la fecha y hora
        String formattedDateTime = limaTime.format(formatter);
        // Concatenando los JSON de clientes en una única cadena

        String safeContent = StringEscapeUtils.escapeJson("Eres NeuraX un asistente especialista en Marketing, Ventas, Gestion de Reportes, tu trabajo es asistir a Iam el dueño de la tienda Moto Facil, a continuacion el Reporte de sus clientes, tener en cuenta las fechas, modelos de motos, marcas, estado segun su financiamiento, esta es la fecha actual ("+formattedDateTime+"), Reporte:\n" + clientesConcatenados);

        // Setup de la conexión HTTP para la API de OpenAI
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + this.obtenerValorTokenPorId(1));

        String requestBody = String.format("{"
                + "\"model\": \"gpt-4o-mini\","
                + "\"messages\": ["
                + "  {\"role\": \"system\", \"content\": \"%s\"},"
                + "  {\"role\": \"user\", \"content\": \"%s\"}"
                + "],"
                + "\"temperature\": 0.7"
                + "}", safeContent, prompt);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");
                return contentNode.asText();
            } catch (Exception e) {
                return "Error al procesar la respuesta JSON: " + e.getMessage();
            }
        } else {
            return "Error al obtener la respuesta de OpenAI: " + response.getStatusCode().toString();
        }
    }

    public String obtenerValorTokenPorId(int id) {
        String sql = "SELECT valor FROM token WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

}
