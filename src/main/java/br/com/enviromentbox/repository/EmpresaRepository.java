package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Empresa;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Empresa entity.
 */
@SuppressWarnings("unused")
public interface EmpresaRepository extends JpaRepository<Empresa,Long> {

}
