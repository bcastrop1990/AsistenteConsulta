package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.service.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("masajesApiController")
@RequestMapping("masajes/api")
@AllArgsConstructor
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/data/{dni}")
    public String getData(@PathVariable("dni") String dni, Model model) {
        return apiService.getApiMessage(dni);
    }
}
