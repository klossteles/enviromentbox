package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.TipoSensor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoSensor entity.
 */
@SuppressWarnings("unused")
public interface TipoSensorRepository extends JpaRepository<TipoSensor,Long> {

}
