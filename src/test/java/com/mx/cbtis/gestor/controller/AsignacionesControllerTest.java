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

import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;

/**
 * Pruebas unitarias de AsignacionesController.
 * El repositorio se simula con Mockito, por lo que no se necesita base de datos.
 */
@ExtendWith(MockitoExtension.class)
class AsignacionesControllerTest {

    @Mock
    private AsignacionesRepository repo;

    @InjectMocks
    private AsignacionesController controller;

    private Asignaciones nuevo(int id) {
        return new Asignaciones(id, 131, 199, "2026-10-14");
    }

    @Test
    @DisplayName("UT-39: Buscar asignacion existente devuelve el registro")
    void buscarExistenteDevuelveElRegistro() {
        Asignaciones esperado = nuevo(1);
        when(repo.findById(1)).thenReturn(Optional.of(esperado));

        Asignaciones resultado = controller.getAsignacion(1);

        assertSame(esperado, resultado);
        verify(repo).findById(1);
    }

    @Test
    @DisplayName("UT-40: Buscar asignacion inexistente devuelve un objeto vacio")
    void buscarInexistenteDevuelveObjetoVacio() {
        when(repo.findById(999999)).thenReturn(Optional.empty());

        Asignaciones resultado = controller.getAsignacion(999999);

        assertNotNull(resultado);
        assertNull(resultado.getId_asignacion());
    }

    @Test
    @DisplayName("UT-41: Agregar asignacion valido guarda el registro y devuelve 1")
    void agregarGuardaYDevuelveUno() {
        Asignaciones dato = nuevo(500);

        int resultado = controller.addAsignacion(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-42: Actualizar asignacion guarda los cambios y devuelve 1")
    void actualizarGuardaYDevuelveUno() {
        Asignaciones dato = nuevo(500);

        int resultado = controller.updateAsignacion(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-43: Eliminar asignacion borra el registro y devuelve 1")
    void eliminarBorraYDevuelveUno() {
        Asignaciones dato = nuevo(500);

        int resultado = controller.deleteAsignacion(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).delete(dato);
    }

    @Test
    @DisplayName("UT-44: Listar asignaciones devuelve todos los registros")
    void listarDevuelveTodos() {
        when(repo.findAll()).thenReturn(Arrays.asList(nuevo(1), nuevo(2)));

        List<Asignaciones> resultado = controller.getAsignaciones();

        assertEquals(2, resultado.size());
        assertEquals(Integer.valueOf(1), resultado.get(0).getId_asignacion());
        assertEquals(Integer.valueOf(2), resultado.get(1).getId_asignacion());
    }

    @Test
    @DisplayName("UT-45: Listar asignaciones sin registros devuelve lista vacia")
    void listarSinRegistrosDevuelveListaVacia() {
        when(repo.findAll()).thenReturn(Collections.<Asignaciones>emptyList());

        List<Asignaciones> resultado = controller.getAsignaciones();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("UT-46: Agregar asignacion nulo lanza NullPointerException y no guarda")
    void agregarNuloLanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> controller.addAsignacion(null));
        verifyNoInteractions(repo);
    }
}
