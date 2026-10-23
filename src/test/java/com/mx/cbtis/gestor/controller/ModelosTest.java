package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.modelo.Usuario;

/**
 * Pruebas unitarias de las entidades: verifican que cada campo se asigna y se lee correctamente.
 */
class ModelosTest {

    @Test
    @DisplayName("UT-47: Usuario conserva los datos asignados con el constructor y los setters")
    void usuarioAsignaYLeeCampos() {
        Usuario u = new Usuario(1, "Ana", "ana@test.mx", "clave", "Estudiante", "#112233");
        assertEquals(Integer.valueOf(1), u.getId_usuario());
        assertEquals("Ana", u.getNombre());
        assertEquals("ana@test.mx", u.getCorreo_electronico());
        assertEquals("clave", u.getContrasena());
        assertEquals("Estudiante", u.getRol());
        assertEquals("#112233", u.getImagen_usuario());

        u.setNombre("Ana Maria");
        u.setRol("Administrador");
        assertEquals("Ana Maria", u.getNombre());
        assertEquals("Administrador", u.getRol());
        assertNull(new Usuario().getId_usuario());
    }

    @Test
    @DisplayName("UT-48: Proyectos conserva los datos asignados con los setters")
    void proyectosAsignaYLeeCampos() {
        Proyectos p = new Proyectos();
        p.setId_proyecto(197);
        p.setNombre("My API Rest");
        p.setDescripcion("API REST");
        p.setFecha_inicio("2026-10-14");
        p.setFecha_finalizacion("2026-12-01");
        p.setEstado_proyecto("En proceso");
        p.setFondo("/Images/prueba.gif");

        assertEquals(Integer.valueOf(197), p.getId_proyecto());
        assertEquals("My API Rest", p.getNombre());
        assertEquals("API REST", p.getDescripcion());
        assertEquals("2026-10-14", p.getFecha_inicio());
        assertEquals("2026-12-01", p.getFecha_finalizacion());
        assertEquals("En proceso", p.getEstado_proyecto());
        assertEquals("/Images/prueba.gif", p.getFondo());
    }

    @Test
    @DisplayName("UT-49: Tareas conserva los datos asignados con los setters")
    void tareasAsignaYLeeCampos() {
        Tareas t = new Tareas();
        t.setId_tarea(199);
        t.setNombre("Base de datos");
        t.setDescripcion("Estructura de las tablas");
        t.setFecha_vencimiento("2026-10-30");
        t.setLista("terminado");
        t.setEstado("danger");
        t.setId_proyecto(197);

        assertEquals(Integer.valueOf(199), t.getId_tarea());
        assertEquals("Base de datos", t.getNombre());
        assertEquals("Estructura de las tablas", t.getDescripcion());
        assertEquals("2026-10-30", t.getFecha_vencimiento());
        assertEquals("terminado", t.getLista());
        assertEquals("danger", t.getEstado());
        assertEquals(Integer.valueOf(197), t.getId_proyecto());
    }

    @Test
    @DisplayName("UT-50: Comentarios conserva los datos asignados con los setters")
    void comentariosAsignaYLeeCampos() {
        Comentarios c = new Comentarios();
        c.setId_comentario(201);
        c.setId_usuario("Usuario Prueba");
        c.setId_tarea(199);
        c.setTexto_comentario("Comentario de prueba");
        c.setFecha_hora("2026-10-14 12:00:00");

        assertEquals(Integer.valueOf(201), c.getId_comentario());
        assertEquals("Usuario Prueba", c.getId_usuario());
        assertEquals(Integer.valueOf(199), c.getId_tarea());
        assertEquals("Comentario de prueba", c.getTexto_comentario());
        assertEquals("2026-10-14 12:00:00", c.getFecha_hora());
    }

    @Test
    @DisplayName("UT-51: Asignaciones conserva los datos asignados con el constructor y los setters")
    void asignacionesAsignaYLeeCampos() {
        Asignaciones a = new Asignaciones(1, 131, 199, "2026-10-14");
        assertEquals(Integer.valueOf(1), a.getId_asignacion());
        assertEquals(Integer.valueOf(131), a.getId_usuario());
        assertEquals(Integer.valueOf(199), a.getId_tarea());
        assertEquals("2026-10-14", a.getFecha_asignacion());

        a.setFecha_asignacion("2026-10-15");
        assertEquals("2026-10-15", a.getFecha_asignacion());

        Asignaciones aVacio = new Asignaciones();
        aVacio.setId_asignacion(10);
        aVacio.setId_usuario(20);
        aVacio.setId_tarea(30);
        assertEquals(Integer.valueOf(10), aVacio.getId_asignacion());
        assertEquals(Integer.valueOf(20), aVacio.getId_usuario());
        assertEquals(Integer.valueOf(30), aVacio.getId_tarea());
    }

    @Test
    @DisplayName("UT-52: Constructores con parámetros de Proyectos, Tareas y Comentarios")
    void constructoresCompletos() {
        Proyectos p = new Proyectos(1, "P1", "Desc", "2026-01-01", "2026-02-01", "Activo", "fondo.png");
        assertEquals(Integer.valueOf(1), p.getId_proyecto());
        assertEquals("P1", p.getNombre());

        Tareas t = new Tareas(2, "T1", "DescT", "2026-03-01", "pendiente", "warning", 1);
        assertEquals(Integer.valueOf(2), t.getId_tarea());
        assertEquals("T1", t.getNombre());

        Comentarios c = new Comentarios(3, "user1", 2, "comentario", "2026-01-01 10:00:00");
        assertEquals(Integer.valueOf(3), c.getId_comentario());
        assertEquals("user1", c.getId_usuario());
    }
}
