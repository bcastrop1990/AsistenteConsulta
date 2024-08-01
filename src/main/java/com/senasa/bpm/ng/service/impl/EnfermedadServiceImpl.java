package com.senasa.bpm.ng.service.impl;

import com.senasa.bpm.ng.dao.EnfermedadDao;

import com.senasa.bpm.ng.model.Categoria;
import com.senasa.bpm.ng.model.Cliente;
import com.senasa.bpm.ng.model.Marca;
import com.senasa.bpm.ng.model.Producto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class EnfermedadServiceImpl implements EnfermedadService {



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
    public void eliminarCliente(Long id) {
        enfermedadDao.eliminarCliente(id);
    }
    @Override
    public String agregarCategoria(Categoria request) {
        /*if (!sesionDao.validarCodigo(request.getEmail(), request.getCodigo())){
            throw new ApiValidateException("El código de validación expiró.");
        }*/
        return enfermedadDao.agregarCategoria(request);
    }
}
