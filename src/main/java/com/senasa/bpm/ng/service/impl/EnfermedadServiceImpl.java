package com.senasa.bpm.ng.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.senasa.bpm.ng.dao.EnfermedadDao;

import com.senasa.bpm.ng.model.*;
import com.senasa.bpm.ng.model.bean.EnfermedadesBean;

import com.senasa.bpm.ng.model.request.GrabarSolRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.EnfermedadResponse;
import com.senasa.bpm.ng.model.response.EnfermedadesPrimariasResponse;

import com.senasa.bpm.ng.service.EnfermedadService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class EnfermedadServiceImpl implements EnfermedadService {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EnfermedadDao enfermedadDao;

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
    public void eliminarProducto(Long id) {
        enfermedadDao.eliminarProducto(id);
    }
    @Override
    public List<Cliente> listarClientes() {
        return enfermedadDao.listarClientes();
    }
    @Override
    public List<Cliente> listarClientesMotoFacil() {
        return enfermedadDao.listarClientesMotoFacil();
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
}
