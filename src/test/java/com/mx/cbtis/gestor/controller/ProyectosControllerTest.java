package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProtectosRepository;

/**
 * Pruebas unitarias de ProyectosController.
 * El repositorio se simula con Mockito, por lo que no se necesita base de datos.
 */
@ExtendWith(MockitoExtension.class)
class ProyectosControllerTest {

    @Mock
    private ProtectosRepository repo;

    @InjectMocks
    private ProyectosController controller;

    private Proyectos nuevo(int id) {
        Proyectos p = new Proyectos();
        p.setId_proyecto(id);
        p.setNombre("Proyecto " + id);
        p.setDescripcion("Descripcion de prueba");
        p.setFecha_inicio("2026-10-14");
        p.setFecha_finalizacion("2026-12-01");
        p.setEstado_proyecto("En proceso");
        p.setFondo("/Images/prueba.gif");
        return p;
    }

    @Test
    @DisplayName("UT-11: Buscar proyecto existente devuelve el registro")
    void buscarExistenteDevuelveElRegistro() {
        Proyectos esperado = nuevo(197);
        when(repo.findById(197)).thenReturn(Optional.of(esperado));

        Proyectos resultado = controller.getProyecto(197);

        assertSame(esperado, resultado);
        verify(repo).findById(197);
    }

    @Test
    @DisplayName("UT-12: Buscar proyecto inexistente devuelve un objeto vacio")
    void buscarInexistenteDevuelveObjetoVacio() {
        when(repo.findById(999999)).thenReturn(Optional.empty());

        Proyectos resultado = controller.getProyecto(999999);

        assertNotNull(resultado);
        assertNull(resultado.getId_proyecto());
    }

    @Test
    @DisplayName("UT-13: Agregar proyecto valido guarda el registro y devuelve 1")
    void agregarGuardaYDevuelveUno() {
        Proyectos dato = nuevo(500);

        int resultado = controller.addProyecto(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-14: Actualizar proyecto guarda los cambios y devuelve 1")
    void actualizarGuardaYDevuelveUno() {
        Proyectos dato = nuevo(500);

        int resultado = controller.updateProyecto(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-15: Eliminar proyecto borra el registro y devuelve 1")
    void eliminarBorraYDevuelveUno() {
        Proyectos dato = nuevo(500);

        int resultado = controller.deleteProyecto(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).delete(dato);
    }

    @Test
    @DisplayName("UT-16: Listar proyectos devuelve todos los registros")
    void listarDevuelveTodos() {
        when(repo.findAll()).thenReturn(Arrays.asList(nuevo(1), nuevo(2)));

        List<Proyectos> resultado = controller.getProyectos();

        assertEquals(2, resultado.size());
        assertEquals(Integer.valueOf(1), resultado.get(0).getId_proyecto());
        assertEquals(Integer.valueOf(2), resultado.get(1).getId_proyecto());
    }

    @Test
    @DisplayName("UT-17: Listar proyectos sin registros devuelve lista vacia")
    void listarSinRegistrosDevuelveListaVacia() {
        when(repo.findAll()).thenReturn(Collections.<Proyectos>emptyList());

        List<Proyectos> resultado = controller.getProyectos();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("UT-18: Agregar proyecto nulo lanza NullPointerException y no guarda")
    void agregarNuloLanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> controller.addProyecto(null));
        verifyNoInteractions(repo);
    }
}
