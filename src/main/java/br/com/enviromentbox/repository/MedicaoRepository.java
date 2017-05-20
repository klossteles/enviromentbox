package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Medicao entity.
 */
@SuppressWarnings("unused")
public interface MedicaoRepository extends JpaRepository<Medicao,Long> {

}
