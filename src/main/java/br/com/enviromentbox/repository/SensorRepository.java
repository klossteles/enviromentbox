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
public interface SensorRepository extends JpaRepository<Sensor,Long> {

    @Query(value = "select s.id, ts.nome, s.id_device_id FROM sensor s JOIN tipo_sensor ts ON ts.id = s.id_tipo_sensor WHERE s.id_device_id = :device_id",nativeQuery = true)
    ArrayList<Sensor> consultarSensoresByDeviceId(@Param("device_id") Long device_id);

    @Query(value = "SELECT s.id, ts.nome as nome_sensor, s.id_device_id, d.nome as nome_device, ts.id as id_tipo_sensor FROM sensor s JOIN device d ON d.id = s.id_device_id JOIN tipo_sensor ts ON ts.id = s.id_tipo_sensor",nativeQuery = true)
    ArrayList<Object[]> consultarSensores();
}
