package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.request.ConfiguracionRequest;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.service.ConfiguracionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("masajes/configuracion")
@AllArgsConstructor
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    @PostMapping("/actualizar")
    public ResponseEntity<ApiResponse<Void>> actualizarConfiguracion(@RequestBody ConfiguracionRequest request) {
        ApiResponse<Void> response = configuracionService.actualizarConfiguracion(request);

        if ("200".equals(response.getCode())) {
            return ResponseEntity.ok(response);
        } else {
            // En caso de error, podrías devolver un código HTTP 500 o un código adecuado basado en el tipo de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}



