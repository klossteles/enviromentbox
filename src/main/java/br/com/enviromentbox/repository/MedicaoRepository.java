package br.com.enviromentbox.repository;

import br.com.enviromentbox.domain.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.math.BigDecimal;
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

    @Query(value = "SELECT m.id, m.valor, m.data_hora_medicao, m.device_id, m.sensor_id, ts.nome as tipo_sensor FROM medicao m join sensor s on s.id = m.sensor_id join tipo_sensor ts on ts.id = s.id_tipo_sensor WHERE m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :sensor_id order by m.data_hora_medicao", nativeQuery = true)
    ArrayList<Object[]> consultarMedicoesFiltradas(@Param("device_id") Long device_id, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal, @Param("sensor_id") Long sensor_id);

    @Query(value = "select m.id as id_medicao, m.valor, m.device_id as id_device, m.sensor_id as id_sensor, m.data_hora_medicao from medicao m where m.device_id = :device_id",nativeQuery = true)
    List<Object[]> consultaMedicoesByDeviceId(@Param("device_id") Long device_id);

    @Query(value = "select d.id as id_device, d.nome as nome, e.nome as empresa from device d JOIN empresa e ON e.id = d.id_empresa_id where d.id = :device_id ",nativeQuery = true)
    List<Object[]> consultarByDeviceId(@Param("device_id") Long device_id);

    @Query(value = "select min(m.valor) as minimo from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarMinMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select max(m.valor) as maximo from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarMaxMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select stddev(m.valor) as desvio_padrao from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarStddevMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "select variance(m.valor) as variancia from medicao m where m.device_id = :device_id and m.data_hora_medicao >= :dataInicial and m.data_hora_medicao <= :dataFinal and m.sensor_id = :id_sensor", nativeQuery = true)
    String consultarVarianceMedicaoDeviceFiltrado(@Param("device_id") Long device_id, @Param("id_sensor") Long idSensor, @Param("dataInicial") @Temporal(TemporalType.TIMESTAMP) Date dataInicial, @Param("dataFinal") @Temporal(TemporalType.TIMESTAMP) Date dataFinal);

    @Query(value = "SELECT avg(m.valor) as media FROM medicao m JOIN sensor s ON s.id = m.sensor_id JOIN tipo_sensor ts ON ts.id = s.id_tipo_sensor WHERE m.device_id = :device_id AND ts.id= :id_tipo_sensor AND m.data_hora_medicao >= :data_hora_medicao", nativeQuery = true)
    BigDecimal consultaMediaAlerta(@Param("device_id") Long device_id, @Param("id_tipo_sensor") Long id_tipo_sensor, @Param("data_hora_medicao") @Temporal(TemporalType.TIMESTAMP) Date data_hora_medicao);

    @Query(value = "select m.valor as valor from medicao m join sensor s on s.id = m.sensor_id join tipo_sensor ts on ts.id = s.id_tipo_sensor where m.device_id = :device_id  and ts.id = :id_tipo_sensor ORDER BY data_hora_medicao DESC limit 3", nativeQuery = true)
    List<BigDecimal> consultarMediaAlertaMonoxido(@Param("device_id") Long device_id, @Param("id_tipo_sensor") Long id_tipo_sensor);
}
