package com.senasa.bpm.ng.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.senasa.bpm.ng.model.*;
import com.senasa.bpm.ng.model.request.*;
import com.senasa.bpm.ng.model.response.*;

import java.util.Date;
import java.util.List;

public interface EnfermedadService {

    List<EnfermedadesPrimariasResponse> listarEnfermedadesPrimarias();
    List<EnfermedadResponse> listarPorEnfrPr(Long id_EnfPr);
    List<Producto> listarProducto();
    List<Producto> listarProductoPorMarca(Long id_EnfPr);
    List<Marca> listarMarcas();
    String agregarProd(Producto request);
    String agregarCategoria(Categoria request);
    void eliminarProducto(Long id);
    List<Cliente> listarClientes();
    List<Cliente> listarClientesMotoFacil();
    void eliminarCliente(Long id);
    void eliminarCategoria(Long id);
    List<Venta> listarPorFecha(Date desde, Date hasta);
    List<Venta> listarTodo();
    List<Venta> listarPorMes(int mes, int año);
    List<Venta> listarPorAño(int año);
    List<Venta> listarPorDia(int dia, int mes, int año);
    String getApiData(String dni);
    void guardar(String celular, String nombres, String apellidos, String estado);
    void guardarInfoMotoFacil(String celular, String nombre_completo, String ubicacion, String tipo_compra, String cuota_inicial, String modelo, String marca, String email);
    void guardarPrimero(String celular);
}
