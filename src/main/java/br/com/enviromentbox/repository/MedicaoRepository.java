package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the Medicao entity.
 */
@SuppressWarnings("unused")
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {

    @Query(value = "select avg(m.valor) as media from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarMediaMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select m.id, m.valor, m.device_id, m.sensor_id, m.data_hora_medicao from medicao m where m.device_id = :device_id and m.data_hora_medicao BETWEEN :dataInicial and :dataFinal and m.sensor_id = :sensor_id", nativeQuery = true)
    ArrayList<Medicao> consultarMedicoesFiltradas(@Param("device_id") Long device_id, @Param("sensor_id") Long sensor_id, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select m.id as id_medicao, m.valor, m.device_id as id_device, m.sensor_id as id_sensor, m.data_hora_medicao from medicao m where m.device_id = ?1",nativeQuery = true)
    List<Object[]> consultaMedicoesByDeviceId(Long device_id);

    @Query(value = "select min(m.valor) as minimo from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarMinMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select max(m.valor) as maximo from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarMaxMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select stddev(m.valor) as desvio_padrao from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarStddevMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select variance(m.valor) as variancia from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarVarianceMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);
}
