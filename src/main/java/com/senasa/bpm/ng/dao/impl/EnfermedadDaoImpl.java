
package com.senasa.bpm.ng.dao.impl;


import com.senasa.bpm.ng.dao.EnfermedadDao;
import com.senasa.bpm.ng.dao.rowmapper.*;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.Categoria;
import com.senasa.bpm.ng.model.Cliente;
import com.senasa.bpm.ng.model.Marca;
import com.senasa.bpm.ng.model.Producto;
import com.senasa.bpm.ng.model.bean.EnfermedadesBean;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.EnfermedadResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@Service
@Slf4j
public class EnfermedadDaoImpl implements EnfermedadDao {

    private JdbcTemplate jdbcTemplate;

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
        String sql = "select producto.*,  marca.nombre as nombre_marca from producto inner join marca on producto.marca = marca.id where producto.marca = ?";
        try {
            return jdbcTemplate.query(sql, new ProductoRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();}
    }
    @Override
    public List<Marca> listarMarcas() {
        String sql = "SELECT * FROM marca";
        try {
            return jdbcTemplate.query(sql, new MarcaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // Retornar una lista vacía en lugar de Optional.empty() cuando no hay resultados
            return Collections.emptyList();}
    }
    @Override
    public String agregarProd(Producto request) {
        String codigo = request.getCodigo();
        String sqlCount = "SELECT COUNT(*) FROM producto WHERE codigo = ?";
        if (jdbcTemplate.queryForObject(sqlCount, Integer.class, codigo) >= 1){
            throw new ApiValidateException("El codigo: "+codigo+" ya se encuentra registrado.");
        }

        String sql = "INSERT INTO producto (nombre, marca, descrp, precio, imagen, codigo, estado) VALUES (?, ?, ?, ?, ?, ?, 0)";
        return String.valueOf(jdbcTemplate.update(sql, request.getNombre(), request.getMarca(), request.getDesc(),
                request.getPrecio(), request.getImagen(),request.getCodigo()));
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
}
