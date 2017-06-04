package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

/**
 * Spring Data JPA repository for the Sensor entity.
 */
@SuppressWarnings("unused")
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query(value = "select s.id, s.nome, s.id_device_id from sensor s where s.id_device_id = :device_id",nativeQuery = true)
    ArrayList<Sensor> consultarSensoresByDeviceId(@Param("device_id") Long device_id);
}
