package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class HomeControllerTest {

    @Test
    @DisplayName("UT-55: HomeController root / devuelve estado UP y metadatos del servicio")
    void rootDevuelveEstadoUp() {
        HomeController controller = new HomeController();
        ResponseEntity<Map<String, Object>> response = controller.root();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().get("status"));
        assertEquals("21 LTS", response.getBody().get("javaVersion"));
    }
}
