package com.mx.cbtis.gestor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mx.cbtis.gestor.modelo.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	@Query("SELECT u FROM Usuario u WHERE u.correo_electronico = :correo_electronico AND u.contrasena = :contrasena")
	Optional<Usuario> findByCorreoPass(@Param("correo_electronico") String correo_electronico,
			@Param("contrasena") String contrasena);

}
