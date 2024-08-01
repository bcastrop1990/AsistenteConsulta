package com.senasa.bpm.ng.service;

import com.senasa.bpm.ng.model.Categoria;
import com.senasa.bpm.ng.model.Cliente;
import com.senasa.bpm.ng.model.Marca;
import com.senasa.bpm.ng.model.Producto;
import com.senasa.bpm.ng.model.request.*;
import com.senasa.bpm.ng.model.response.*;

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
    void eliminarCliente(Long id);

}
