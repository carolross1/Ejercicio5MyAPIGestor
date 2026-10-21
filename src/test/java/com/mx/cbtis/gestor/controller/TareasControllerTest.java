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

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;

/**
 * Pruebas unitarias de TareasController.
 * El repositorio se simula con Mockito, por lo que no se necesita base de datos.
 */
@ExtendWith(MockitoExtension.class)
class TareasControllerTest {

    @Mock
    private TareasRepository repo;

    @InjectMocks
    private TareasController controller;

    private Tareas nuevo(int id) {
        Tareas t = new Tareas();
        t.setId_tarea(id);
        t.setNombre("Tarea " + id);
        t.setDescripcion("Descripcion de prueba");
        t.setFecha_vencimiento("2026-10-30");
        t.setLista("pendiente");
        t.setEstado("warning");
        t.setId_proyecto(197);
        return t;
    }

    @Test
    @DisplayName("UT-19: Buscar tarea existente devuelve el registro")
    void buscarExistenteDevuelveElRegistro() {
        Tareas esperado = nuevo(199);
        when(repo.findById(199)).thenReturn(Optional.of(esperado));

        Tareas resultado = controller.getTarea(199);

        assertSame(esperado, resultado);
        verify(repo).findById(199);
    }

    @Test
    @DisplayName("UT-20: Buscar tarea inexistente devuelve un objeto vacio")
    void buscarInexistenteDevuelveObjetoVacio() {
        when(repo.findById(999999)).thenReturn(Optional.empty());

        Tareas resultado = controller.getTarea(999999);

        assertNotNull(resultado);
        assertNull(resultado.getId_tarea());
    }

    @Test
    @DisplayName("UT-21: Agregar tarea valido guarda el registro y devuelve 1")
    void agregarGuardaYDevuelveUno() {
        Tareas dato = nuevo(500);

        int resultado = controller.addTareas(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-22: Actualizar tarea guarda los cambios y devuelve 1")
    void actualizarGuardaYDevuelveUno() {
        Tareas dato = nuevo(500);

        int resultado = controller.updateTareas(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-23: Eliminar tarea borra el registro y devuelve 1")
    void eliminarBorraYDevuelveUno() {
        Tareas dato = nuevo(500);

        int resultado = controller.deleteTareas(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).delete(dato);
    }

    @Test
    @DisplayName("UT-24: Listar tareas devuelve todos los registros")
    void listarDevuelveTodos() {
        when(repo.findAll()).thenReturn(Arrays.asList(nuevo(1), nuevo(2)));

        List<Tareas> resultado = controller.getTareas();

        assertEquals(2, resultado.size());
        assertEquals(Integer.valueOf(1), resultado.get(0).getId_tarea());
        assertEquals(Integer.valueOf(2), resultado.get(1).getId_tarea());
    }

    @Test
    @DisplayName("UT-25: Listar tareas sin registros devuelve lista vacia")
    void listarSinRegistrosDevuelveListaVacia() {
        when(repo.findAll()).thenReturn(Collections.<Tareas>emptyList());

        List<Tareas> resultado = controller.getTareas();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("UT-26: Agregar tarea nulo lanza NullPointerException y no guarda")
    void agregarNuloLanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> controller.addTareas(null));
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("UT-27: Consultar tareas de un proyecto devuelve las tareas del filtro")
    void filtroDevuelveRegistros() {
        Tareas filtro = new Tareas();
        filtro.setId_proyecto(197);
        when(repo.findByIdProyecto(197)).thenReturn(Arrays.asList(nuevo(1), nuevo(2)));

        List<Tareas> resultado = controller.getTarea(filtro);

        assertEquals(2, resultado.size());
        verify(repo).findByIdProyecto(197);
    }

    @Test
    @DisplayName("UT-28: Consultar tareas de un proyecto sin coincidencias devuelve lista vacia")
    void filtroSinCoincidenciasDevuelveListaVacia() {
        Tareas filtro = new Tareas();
        filtro.setId_proyecto(999999);
        when(repo.findByIdProyecto(999999)).thenReturn(Collections.<Tareas>emptyList());

        List<Tareas> resultado = controller.getTarea(filtro);

        assertTrue(resultado.isEmpty());
    }
}
