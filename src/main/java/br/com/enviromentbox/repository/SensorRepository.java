package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Sensor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sensor entity.
 */
@SuppressWarnings("unused")
public interface SensorRepository extends JpaRepository<Sensor,Long> {

}
