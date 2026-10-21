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

import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.repository.ComentariosRepository;

/**
 * Pruebas unitarias de ComentariosController.
 * El repositorio se simula con Mockito, por lo que no se necesita base de datos.
 */
@ExtendWith(MockitoExtension.class)
class ComentariosControllerTest {

    @Mock
    private ComentariosRepository repo;

    @InjectMocks
    private ComentariosController controller;

    private Comentarios nuevo(int id) {
        Comentarios c = new Comentarios();
        c.setId_comentario(id);
        c.setId_usuario("Usuario Prueba");
        c.setId_tarea(199);
        c.setTexto_comentario("Comentario " + id);
        c.setFecha_hora("2026-10-14 12:00:00");
        return c;
    }

    @Test
    @DisplayName("UT-29: Buscar comentario existente devuelve el registro")
    void buscarExistenteDevuelveElRegistro() {
        Comentarios esperado = nuevo(201);
        when(repo.findById(201)).thenReturn(Optional.of(esperado));

        Comentarios resultado = controller.getComentario(201);

        assertSame(esperado, resultado);
        verify(repo).findById(201);
    }

    @Test
    @DisplayName("UT-30: Buscar comentario inexistente devuelve un objeto vacio")
    void buscarInexistenteDevuelveObjetoVacio() {
        when(repo.findById(999999)).thenReturn(Optional.empty());

        Comentarios resultado = controller.getComentario(999999);

        assertNotNull(resultado);
        assertNull(resultado.getId_comentario());
    }

    @Test
    @DisplayName("UT-31: Agregar comentario valido guarda el registro y devuelve 1")
    void agregarGuardaYDevuelveUno() {
        Comentarios dato = nuevo(500);

        int resultado = controller.addComentario(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-32: Actualizar comentario guarda los cambios y devuelve 1")
    void actualizarGuardaYDevuelveUno() {
        Comentarios dato = nuevo(500);

        int resultado = controller.updateComentario(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-33: Eliminar comentario borra el registro y devuelve 1")
    void eliminarBorraYDevuelveUno() {
        Comentarios dato = nuevo(500);

        int resultado = controller.deleteComentario(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).delete(dato);
    }

    @Test
    @DisplayName("UT-34: Listar comentarios devuelve todos los registros")
    void listarDevuelveTodos() {
        when(repo.findAll()).thenReturn(Arrays.asList(nuevo(1), nuevo(2)));

        List<Comentarios> resultado = controller.getComentarios();

        assertEquals(2, resultado.size());
        assertEquals(Integer.valueOf(1), resultado.get(0).getId_comentario());
        assertEquals(Integer.valueOf(2), resultado.get(1).getId_comentario());
    }

    @Test
    @DisplayName("UT-35: Listar comentarios sin registros devuelve lista vacia")
    void listarSinRegistrosDevuelveListaVacia() {
        when(repo.findAll()).thenReturn(Collections.<Comentarios>emptyList());

        List<Comentarios> resultado = controller.getComentarios();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("UT-36: Agregar comentario nulo lanza NullPointerException y no guarda")
    void agregarNuloLanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> controller.addComentario(null));
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("UT-37: Consultar comentarios de una tarea devuelve las comentarios del filtro")
    void filtroDevuelveRegistros() {
        Comentarios filtro = new Comentarios();
        filtro.setId_tarea(199);
        when(repo.findByTarea(199)).thenReturn(Arrays.asList(nuevo(1), nuevo(2)));

        List<Comentarios> resultado = controller.getComentario(filtro);

        assertEquals(2, resultado.size());
        verify(repo).findByTarea(199);
    }

    @Test
    @DisplayName("UT-38: Consultar comentarios de una tarea sin coincidencias devuelve lista vacia")
    void filtroSinCoincidenciasDevuelveListaVacia() {
        Comentarios filtro = new Comentarios();
        filtro.setId_tarea(999999);
        when(repo.findByTarea(999999)).thenReturn(Collections.<Comentarios>emptyList());

        List<Comentarios> resultado = controller.getComentario(filtro);

        assertTrue(resultado.isEmpty());
    }
}
