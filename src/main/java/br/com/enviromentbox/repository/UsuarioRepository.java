package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Usuario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Usuario entity.
 */
@SuppressWarnings("unused")
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

}
