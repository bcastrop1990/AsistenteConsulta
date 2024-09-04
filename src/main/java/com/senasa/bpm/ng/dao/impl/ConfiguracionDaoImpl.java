
package com.senasa.bpm.ng.dao.impl;

import com.senasa.bpm.ng.dao.ConfiguracionDao;
import com.senasa.bpm.ng.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@Service
@Slf4j
public class ConfiguracionDaoImpl implements ConfiguracionDao {

     private JdbcTemplate jdbcTemplate;


    @Override
    public ApiResponse<Void> actualizarConfiguracion(ConfiguracionRequest request) {
        try {
            String updateSql = "UPDATE configuracion_ia SET max_clientes_activos = ?, contador_activos = 0 WHERE id = 1";
            jdbcTemplate.update(updateSql, request.getMaxClientesActivos());

            // Si la actualización es exitosa, retornamos un ApiResponse con éxito
            return ApiResponse.<Void>builder()
                    .code("200")
                    .message("Configuración actualizada correctamente")
                    .data(null)
                    .build();

        } catch (DataAccessException e) {
            // Capturamos cualquier excepción relacionada con la base de datos
            return ApiResponse.<Void>builder()
                    .code("500")
                    .message("Error al actualizar la configuración: " + e.getMessage())
                    .data(null)
                    .build();
        } catch (Exception e) {
            // Capturamos cualquier otra excepción que pueda ocurrir
            return ApiResponse.<Void>builder()
                    .code("500")
                    .message("Ocurrió un error inesperado: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }



}

