
package com.senasa.bpm.ng.masajes.dao.impl;

import com.senasa.bpm.ng.masajes.dao.MasajesConfiguracionDao;
import com.senasa.bpm.ng.masajes.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class MasajesConfiguracionDaoImpl implements MasajesConfiguracionDao {

     private JdbcTemplate secondaryJdbcTemplate;


    @Override
    public ApiResponse<Void> actualizarConfiguracion(ConfiguracionRequest request) {
        try {
            String updateSql = "UPDATE configuracion_ia SET max_clientes_activos = ?, contador_activos = 0 WHERE id = 1";
            secondaryJdbcTemplate.update(updateSql, request.getMaxClientesActivos());

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

