package com.senasa.bpm.ng.masajes.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senasa.bpm.ng.masajes.dao.EnfermedadDao;
import com.senasa.bpm.ng.masajes.model.*;
import com.senasa.bpm.ng.masajes.model.bean.EnfermedadesBean;
import com.senasa.bpm.ng.masajes.model.request.ClienteListarRequest;
import com.senasa.bpm.ng.masajes.model.response.ClienteCubaMedResponse;
import com.senasa.bpm.ng.masajes.model.response.EnfermedadResponse;
import com.senasa.bpm.ng.masajes.model.response.EnfermedadesPrimariasResponse;
import com.senasa.bpm.ng.masajes.service.EnfermedadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EnfermedadServiceImpl implements EnfermedadService {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EnfermedadDao enfermedadDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<EnfermedadesPrimariasResponse> listarEnfermedadesPrimarias() {
        List<EnfermedadesBean> enfermedadesBean = enfermedadDao.listarEnfermedadesPrimarias();
        List<EnfermedadesPrimariasResponse> response = new ArrayList<>();

        for (EnfermedadesBean bean : enfermedadesBean) {
            EnfermedadesPrimariasResponse aaaa = EnfermedadesPrimariasResponse.builder()
                    .id_EnfPr(bean.getId_EnfPr())
                    .nombre(bean.getNombre())
                    .descripcion(bean.getDescripcion())
                    .build();
            response.add(aaaa);
        }
        return response;
    }

    @Override
    public List<EnfermedadResponse> listarPorEnfrPr(Long id) {
        List<EnfermedadesBean> enfermedadesBean = enfermedadDao.listarPorEnfrPr(id);
        List<EnfermedadResponse> response = new ArrayList<>();

        for (EnfermedadesBean bean : enfermedadesBean) {
            EnfermedadResponse aaaa = EnfermedadResponse.builder()
                    .id_Enf(bean.getId_Enf())
                    .id_EnfPr(bean.getId_EnfPr())
                    .nombre(bean.getNombre())
                    .descripcion(bean.getDescripcion())
                    .build();
            response.add(aaaa);
        }
        return response;
    }
    @Override
    public List<Producto> listarProducto() {
        return enfermedadDao.listarProducto();
    }

    @Override
    public List<Producto> listarProductoPorMarca(Long id) {
        return enfermedadDao.listarProductoPorMarca(id);
    }
    @Override
    public List<Marca> listarMarcas() {
        return enfermedadDao.listarMarcas();
    }
    @Override
    public String agregarProd(Producto request) {
        /*if (!sesionDao.validarCodigo(request.getEmail(), request.getCodigo())){
            throw new ApiValidateException("El código de validación expiró.");
        }*/
        return enfermedadDao.agregarProd(request);
    }

    @Override
    public String cambiarEstado(Activo request) {
        return enfermedadDao.cambiarEstado(request);
    }

    @Override
    public void eliminarProducto(Long id) {
        enfermedadDao.eliminarProducto(id);
    }
    @Override
    public List<Cliente> listarClientes() {
        return enfermedadDao.listarClientes();
    }
    @Override
    public Page<Cliente> listarClientesMotoFacil(ClienteListarRequest request) {
        return enfermedadDao.listarClientesMotoFacil( request.getCelular(),  request.getNombreCompleto(),  request.getUbicacion(), request.getCuotaInicial(), request.getModelo(), request.getMarca(),  request.getEmail(),request.getDni(), request.getTipoCompra(), request.getEstado(), request.getFechaDesde(), request.getFechaHasta(), request.getEstado(), request.getPage());
    }
    @Override
    public void eliminarCliente(Long id) {
        enfermedadDao.eliminarCliente(id);
    }
    @Override
    public void eliminarCategoria(Long id) {
        enfermedadDao.eliminarCategoria(id);
    }
    @Override
    public String agregarCategoria(Categoria request) {
        /*if (!sesionDao.validarCodigo(request.getEmail(), request.getCodigo())){
            throw new ApiValidateException("El código de validación expiró.");
        }*/
        return enfermedadDao.agregarCategoria(request);
    }
    @Override
    public List<Venta> listarPorFecha(Date desde, Date hasta) {
        return enfermedadDao.listarPorFecha(desde, hasta);
    }
    @Override
    public List<Venta> listarTodo() {
        return enfermedadDao.listarTodo();
    }
    @Override
    public List<Venta> listarPorMes(int mes, int año) {
        return enfermedadDao.listarPorMes(mes, año);
    }
    @Override
    public List<Venta> listarPorAño(int año) {
        return enfermedadDao.listarPorAño(año);
    }
    @Override
    public List<Venta> listarPorDia(int dia, int mes, int año) {
        return enfermedadDao.listarPorDia(dia, mes, año);
    }
    @Override
    public String getApiData(String dni) {
        String url = "https://globalgo-api.sis360.com.pe/api/Subscriptions/ins_initial";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestJson = String.format("{\"identity_document_type_id\": 1, \"identity_document_number\": \"%s\", \"code\": \"\", \"birthdate\": \"\"}", dni);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String responseBody = response.getBody();

        // Parse the JSON response to extract the value of "tx_tran_mnsg"
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("tx_tran_mnsg").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }

    @Override
    public List<ClienteCubaMedResponse> obtenerClientesAprobados(List<String> dniList) {
        String url = "https://globalgo-api.sis360.com.pe/api/Subscriptions/ins_initial";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        List<ClienteCubaMedResponse> clientesAprobados = new ArrayList<>();

        for (String dni : dniList) {
            String requestJson = String.format("{\"identity_document_type_id\": 1, \"identity_document_number\": \"%s\", \"code\": \"\", \"birthdate\": \"\"}", dni);
            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                String responseBody = response.getBody();

                // Parse the JSON response to extract the value of "nu_tran_stdo"
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                int estado = jsonNode.get("nu_tran_stdo").asInt();

                // Verificar si el estado es 1 (aprobado)
                if (estado == 1) {
                    // Si está aprobado, obtener la información del cliente desde la base de datos
                    String sql = "SELECT * FROM clientes_moto_facil WHERE dni = ?";
                    try {
                        ClienteCubaMedResponse cliente = jdbcTemplate.queryForObject(sql, new Object[]{dni}, (rs, rowNum) -> {
                            ClienteCubaMedResponse responseCliente = new ClienteCubaMedResponse();
                            responseCliente.setId(rs.getLong("id"));
                            responseCliente.setNombreCompleto(rs.getString("nombre_completo"));
                            responseCliente.setEstado(rs.getString("estado"));
                            responseCliente.setDni(rs.getString("dni"));
                            responseCliente.setTipoCompra(rs.getString("tipo_compra"));
                            responseCliente.setCuotaInicial(rs.getString("cuota_inicial"));
                            responseCliente.setModelo(rs.getString("modelo"));
                            responseCliente.setMarca(rs.getString("marca"));
                            responseCliente.setCelular(rs.getString("celular"));
                            responseCliente.setUbicacion(rs.getString("ubicacion"));
                            responseCliente.setFecha(rs.getDate("fecha"));
                            responseCliente.setEmail(rs.getString("email"));
                            responseCliente.setActivo(rs.getBoolean("activo"));
                            return responseCliente;
                        });

                        clientesAprobados.add(cliente);
                    } catch (EmptyResultDataAccessException e) {
                        // Manejar el caso donde no se encontró el cliente
                        System.out.println("Cliente con DNI " + dni + " no encontrado en la base de datos.");
                    }
                }
            } catch (Exception e) {
                // Manejar cualquier otro tipo de excepción
                System.out.println("Error al procesar el DNI " + dni + ": " + e.getMessage());
            }
        }

        return clientesAprobados;
    }







    @Override
    public void guardar(String celular, String nombres, String apellidos, String estado) {

        enfermedadDao.guardar(celular, nombres, apellidos, estado);
    }
    @Override
    public void guardarInfoMotoFacil(String celular, String nombre_completo, String ubicacion, String tipo_compra, String cuota_inicial, String modelo, String marca, String email){

        enfermedadDao.guardarInfoMotoFacil(celular, nombre_completo, ubicacion, tipo_compra, cuota_inicial, modelo, marca, email);
    }
    @Override
    public void guardarPrimero(String celular) {

        enfermedadDao.guardarPrimero(celular);
    }
    @Override
    public Datos obtenerDatos() {

        return enfermedadDao.obtenerDatos();

    }

    @Override
    public DatosImportantes listarDatosImportantes(){

        return enfermedadDao.listarDatosImportantes();

    }
    @Override
    public String obtenerRespuestaIA(String request) {

        return enfermedadDao.getAnswer(request);
    }
}
