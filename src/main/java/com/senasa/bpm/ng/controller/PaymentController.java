package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.Cita;
import com.senasa.bpm.ng.model.request.PaymentRequest;
import com.senasa.bpm.ng.model.response.ApiResponse;
import com.senasa.bpm.ng.model.response.PaymentResponse;
import com.senasa.bpm.ng.service.PaymentService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/createCharge")
    public ResponseEntity<String> createCharge(@RequestBody PaymentRequest chargeRequest) {
        try {
            String apiKey = paymentService.obtenerSecretKey();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(apiKey, "");

            String url = "https://api.openpay.pe/v1/" + paymentService.obtenerMerchantId() + "/charges";
            HttpEntity<PaymentRequest> request = new HttpEntity<>(chargeRequest, headers);
            String result = restTemplate.postForObject(url, request, String.class);

            return ResponseEntity.ok(result);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }
    @GetMapping("/generarIdTransaccion/{numero_Transaccion}")
    public ResponseEntity<ApiResponse<Integer>> obtenerDetalle(@PathVariable String numero_Transaccion) {
        int idTransaccion = paymentService.obtenerIdTransaccion(numero_Transaccion);
        Integer idTransaccionObjeto = Integer.valueOf(idTransaccion);
        return ResponseEntity.ok(
                ApiResponse.<Integer>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(idTransaccionObjeto)
                        .build());
    }




}
