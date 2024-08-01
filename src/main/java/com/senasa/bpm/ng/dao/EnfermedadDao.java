package com.senasa.bpm.ng.dao;

import com.senasa.bpm.ng.model.Categoria;
import com.senasa.bpm.ng.model.Cliente;
import com.senasa.bpm.ng.model.Marca;
import com.senasa.bpm.ng.model.Producto;
import com.senasa.bpm.ng.model.bean.EnfermedadesBean;

import com.senasa.bpm.ng.model.response.EnfermedadResponse;
import com.senasa.bpm.ng.model.response.EnfermedadesPrimariasResponse;

import java.util.List;

public interface EnfermedadDao {
    List<EnfermedadesBean> listarEnfermedadesPrimarias();
    List<EnfermedadesBean> listarPorEnfrPr(Long id_EnfPr);
    List<Producto> listarProducto();
    List<Producto> listarProductoPorMarca(Long id_EnfPr);
    List<Marca> listarMarcas();
    String agregarProd(Producto request);
    void eliminarProducto(Long id);
    void eliminarCliente(Long id);
    List<Cliente> listarClientes();
    String agregarCategoria(Categoria request);

}
