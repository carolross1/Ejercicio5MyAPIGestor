package com.mx.cbtis.gestor.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final UsuarioRepository usuarioRepository;
	private final ProtectosRepository protectosRepository;
	private final TareasRepository tareasRepository;

	public DataInitializer(UsuarioRepository usuarioRepository,
			ProtectosRepository protectosRepository,
			TareasRepository tareasRepository) {
		this.usuarioRepository = usuarioRepository;
		this.protectosRepository = protectosRepository;
		this.tareasRepository = tareasRepository;
	}

	@Override
	public void run(String... args) {
		try {
			if (usuarioRepository.count() == 0) {
				usuarioRepository.save(new Usuario(131, "Ana Paola Ramírez Vázquez", "ana22533@cbtis75.edu.mx", "0722", "Estudiante", "#70FAB7"));
				usuarioRepository.save(new Usuario(172, "Liliana Gómez Martínez", "liliana22512@cbtis75.edu.mx", "ada", "Estudiante", "#54EA01"));
				usuarioRepository.save(new Usuario(196, "Perla Joceline Martínez Meza", "perla22522@cbtis75.edu.mx", "12345", "Estudiante", "#805611"));
			}
			if (protectosRepository.count() == 0) {
				protectosRepository.save(new Proyectos(197, "My API Rest", "Desarrollaran un Api Rest, que satisfaga un sistema de información con al menos 5 tablas.", "2024-05-31", "2024-06-29", "En proceso", "/Images/animacion1.gif"));
			}
			if (tareasRepository.count() == 0) {
				tareasRepository.save(new Tareas(199, "Base de datos", "Realizar la estructura de las tablas", "2024-06-13", "danger", "terminado", 197));
			}
		} catch (Exception e) {
			System.err.println("Aviso en inicialización de datos: " + e.getMessage());
		}
	}
}
