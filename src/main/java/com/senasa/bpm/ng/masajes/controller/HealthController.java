package com.senasa.bpm.ng.masajes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/masajes/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}