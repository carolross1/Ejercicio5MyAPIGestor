package com.mx.cbtis.gestor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "API Gestor de Proyectos (Backend REST)");
        response.put("javaVersion", "21 LTS");
        response.put("platform", "Render Cloud");
        response.put("actuatorHealth", "/actuator/health");
        response.put("swaggerDoc", "/swagger-ui/index.html");
        return ResponseEntity.ok(response);
    }
}
