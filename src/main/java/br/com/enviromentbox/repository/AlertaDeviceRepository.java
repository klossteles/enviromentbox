package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.AlertaDevice;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

/**
 * Spring Data JPA repository for the AlertaDevice entity.
 */
@SuppressWarnings("unused")
public interface AlertaDeviceRepository extends JpaRepository<AlertaDevice,Long> {

    @Query(value = "select ad.id as id_alerta, d.nome as nome_device, ad.data_hora from alerta_device ad join device d on d.id = ad.device_id join sensor s on s.id = ad.sensor_id and ad.processado = 0 and d.id = :device and s.id = :sensor  ", nativeQuery = true)
    ArrayList<Object> consultarAlertaDeviceNaoProcessado(@Param("device") Long device, @Param("sensor") Long sensor);

    @Query(value = "SELECT ad.id AS id_alerta, d.nome AS nome_device, ts.nome AS nome_sensor, ad.data_hora FROM alerta_device ad JOIN device d ON ad.device_id = d.id JOIN sensor s ON s.id = ad.sensor_id JOIN tipo_sensor ts on ts.id = s.id_tipo_sensor WHERE ad.device_id = :device_id AND ad.processado = 0", nativeQuery = true)
    ArrayList<Object[]> consultarAlertasNaoProcessadosByDeviceId(@Param("device_id") Long device_id);

    @Query(value = "update alerta_device set processado = 1 where id = :id_alerta", nativeQuery = true)
    void processarAlerta(@Param("id_alerta") Long id_alerta);
}
