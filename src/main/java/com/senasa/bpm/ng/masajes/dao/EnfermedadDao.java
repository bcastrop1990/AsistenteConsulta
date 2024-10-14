package com.senasa.bpm.ng.masajes.dao;

import com.senasa.bpm.ng.masajes.model.*;
import com.senasa.bpm.ng.masajes.model.bean.EnfermedadesBean;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EnfermedadDao {
    List<EnfermedadesBean> listarEnfermedadesPrimarias();
    List<EnfermedadesBean> listarPorEnfrPr(Long id_EnfPr);
    List<Producto> listarProducto();
    List<Producto> listarProductoPorMarca(Long id_EnfPr);
    List<Marca> listarMarcas();
    String agregarProd(Producto request);
    String cambiarEstado(Activo request);

    void eliminarProducto(Long id);
    void eliminarCliente(Long id);
    void eliminarCategoria(Long id);
    List<Cliente> listarClientes();
    Datos obtenerDatos();
    DatosImportantes listarDatosImportantes();

    Page<Cliente> listarClientesMotoFacil(String celular, String nombre_completo, String ubicacion, String cuota_inicial, String modelo, String marca, String email, String dni, String tipo_compra, String estado, LocalDate fechaDesde, LocalDate fechaHasta, String activo, int page);
    String agregarCategoria(Categoria request);
    List<Venta> listarPorFecha(Date desde, Date hasta);
    List<Venta> listarTodo();
    List<Venta> listarPorMes(int mes, int año);
    List<Venta> listarPorAño(int año);
    List<Venta> listarPorDia(int dia, int mes, int año);
    void guardar(String celular, String nombres, String apellidos, String estado);
    void guardarInfoMotoFacil(String celular, String nombre_completo, String ubicacion, String tipo_compra, String cuota_inicial, String modelo, String marca, String email);
    void guardarPrimero(String celular);
    String getAnswer(String pregunta);
}
