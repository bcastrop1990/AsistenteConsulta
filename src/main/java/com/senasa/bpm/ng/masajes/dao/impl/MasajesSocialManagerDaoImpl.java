
package com.senasa.bpm.ng.masajes.dao.impl;


import com.senasa.bpm.ng.masajes.dao.MasajesSocialManagerDao;
import com.senasa.bpm.ng.masajes.model.EstadoFinanciamiento;
import com.senasa.bpm.ng.masajes.model.MarcaMotoFacil;
import com.senasa.bpm.ng.masajes.model.ModeloMotoFacil;
import com.senasa.bpm.ng.masajes.model.TipoDeCompra;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class MasajesSocialManagerDaoImpl implements MasajesSocialManagerDao {

    private JdbcTemplate secondaryJdbcTemplate;


    public List<EstadoFinanciamiento> listarEstadosFinanciamiento() {
        String sql = "SELECT codigo, descripcion FROM ESTADO_FINANCIAMIENTO";
        return secondaryJdbcTemplate.query(sql, new RowMapper<EstadoFinanciamiento>() {
            @Override
            public EstadoFinanciamiento mapRow(ResultSet rs, int rowNum) throws SQLException {
                EstadoFinanciamiento estado = new EstadoFinanciamiento();
                estado.setCodigo(rs.getInt("codigo"));
                estado.setDescripcion(rs.getString("descripcion"));
                return estado;
            }
        });
    }

    public List<TipoDeCompra> listarTipoCompra() {
        String sql = "SELECT codigo, descripcion FROM TIPO_DE_COMPRA_MOTO_FACIL";
        return secondaryJdbcTemplate.query(sql, new RowMapper<TipoDeCompra>() {
            @Override
            public TipoDeCompra mapRow(ResultSet rs, int rowNum) throws SQLException {
                TipoDeCompra tipo = new TipoDeCompra();
                tipo.setCodigo(rs.getInt("codigo"));
                tipo.setDescripcion(rs.getString("descripcion"));
                return tipo;
            }
        });
    }

    public List<MarcaMotoFacil> listarMarcaMotoFacil() {
        String sql = "SELECT codigo, descripcion FROM MARCA_MOTO_FACIL";
        return secondaryJdbcTemplate.query(sql, new RowMapper<MarcaMotoFacil>() {
            @Override
            public MarcaMotoFacil mapRow(ResultSet rs, int rowNum) throws SQLException {
                MarcaMotoFacil tipo = new MarcaMotoFacil();
                tipo.setCodigo(rs.getInt("codigo"));
                tipo.setDescripcion(rs.getString("descripcion"));
                return tipo;
            }
        });
    }

    public List<ModeloMotoFacil> listarModeloMotoFacil() {
        String sql = "SELECT codigo, descripcion FROM MODELO_MOTO_FACIL";
        return secondaryJdbcTemplate.query(sql, new RowMapper<ModeloMotoFacil>() {
            @Override
            public ModeloMotoFacil mapRow(ResultSet rs, int rowNum) throws SQLException {
                ModeloMotoFacil tipo = new ModeloMotoFacil();
                tipo.setCodigo(rs.getInt("codigo"));
                tipo.setDescripcion(rs.getString("descripcion"));
                return tipo;
            }
        });
    }

}
