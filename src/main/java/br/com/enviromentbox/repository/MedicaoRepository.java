package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

/**
 * Spring Data JPA repository for the Medicao entity.
 */
@SuppressWarnings("unused")
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {

//    @Query("select  avg(valor) from medicao where device_id = :device_id")
//    String consultaMedicaByDevice(@Param("device_id") Long device_id);

    @Query(value = "select m.id, m.valor, m.device_id, m.sensor_id, m.data_hora_medicao from medicao m where m.device_id = ?1",nativeQuery = true)
    List<Medicao> consultaMedicoesByDeviceId(Long device_id);

    @Query(value = "select m.id, m.valor, m.device_id, m.sensor_id, m.data_hora_medicao from medicao m where m.device_id >= ?1 and m.data_hora_medicao <= ?2 and m.data_hora_medicao <= ?3", nativeQuery = true)
    List<Medicao> consultaMedicoesFiltradas(Long deviceId, Timestamp data_hora_inicial, Timestamp data_hora_final);

}
