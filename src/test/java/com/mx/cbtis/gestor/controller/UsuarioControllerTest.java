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

import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

/**
 * Pruebas unitarias de UsuarioController.
 * El repositorio se simula con Mockito, por lo que no se necesita base de datos.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioController controller;

    private Usuario nuevo(int id) {
        return new Usuario(id, "Usuario " + id, "u" + id + "@test.mx", "clave" + id, "Estudiante", "#112233");
    }

    @Test
    @DisplayName("UT-01: Buscar usuario existente devuelve el registro")
    void buscarExistenteDevuelveElRegistro() {
        Usuario esperado = nuevo(131);
        when(repo.findById(131)).thenReturn(Optional.of(esperado));

        Usuario resultado = controller.getUsuario(131);

        assertSame(esperado, resultado);
        verify(repo).findById(131);
    }

    @Test
    @DisplayName("UT-02: Buscar usuario inexistente devuelve un objeto vacio")
    void buscarInexistenteDevuelveObjetoVacio() {
        when(repo.findById(999999)).thenReturn(Optional.empty());

        Usuario resultado = controller.getUsuario(999999);

        assertNotNull(resultado);
        assertNull(resultado.getId_usuario());
    }

    @Test
    @DisplayName("UT-03: Agregar usuario valido guarda el registro y devuelve 1")
    void agregarGuardaYDevuelveUno() {
        Usuario dato = nuevo(500);

        int resultado = controller.addUsuario(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-04: Actualizar usuario guarda los cambios y devuelve 1")
    void actualizarGuardaYDevuelveUno() {
        Usuario dato = nuevo(500);

        int resultado = controller.updateUsuario(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).save(dato);
    }

    @Test
    @DisplayName("UT-05: Eliminar usuario borra el registro y devuelve 1")
    void eliminarBorraYDevuelveUno() {
        Usuario dato = nuevo(500);

        int resultado = controller.deleteUsuario(dato);

        assertEquals(1, resultado);
        verify(repo, times(1)).delete(dato);
    }

    @Test
    @DisplayName("UT-06: Listar usuarios devuelve todos los registros")
    void listarDevuelveTodos() {
        when(repo.findAll()).thenReturn(Arrays.asList(nuevo(1), nuevo(2)));

        List<Usuario> resultado = controller.getUsuarios();

        assertEquals(2, resultado.size());
        assertEquals(Integer.valueOf(1), resultado.get(0).getId_usuario());
        assertEquals(Integer.valueOf(2), resultado.get(1).getId_usuario());
    }

    @Test
    @DisplayName("UT-07: Listar usuarios sin registros devuelve lista vacia")
    void listarSinRegistrosDevuelveListaVacia() {
        when(repo.findAll()).thenReturn(Collections.<Usuario>emptyList());

        List<Usuario> resultado = controller.getUsuarios();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("UT-08: Agregar usuario nulo lanza NullPointerException y no guarda")
    void agregarNuloLanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> controller.addUsuario(null));
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("UT-09: Login con credenciales correctas devuelve el usuario")
    void loginCorrectoDevuelveElUsuario() {
        Usuario esperado = nuevo(131);
        when(repo.findByCorreoPass("u131@test.mx", "clave131")).thenReturn(Optional.of(esperado));
        Usuario credenciales = new Usuario();
        credenciales.setCorreo_electronico("u131@test.mx");
        credenciales.setContrasena("clave131");

        Usuario resultado = controller.getUsuarioLogin(credenciales);

        assertSame(esperado, resultado);
        assertNotEquals("000", resultado.getNombre());
    }

    @Test
    @DisplayName("UT-10: Login con credenciales incorrectas devuelve nombre 000")
    void loginIncorrectoDevuelveNombre000() {
        when(repo.findByCorreoPass("x@test.mx", "mala")).thenReturn(Optional.empty());
        Usuario credenciales = new Usuario();
        credenciales.setCorreo_electronico("x@test.mx");
        credenciales.setContrasena("mala");

        Usuario resultado = controller.getUsuarioLogin(credenciales);

        assertEquals("000", resultado.getNombre());
        assertNull(resultado.getId_usuario());
    }
}
