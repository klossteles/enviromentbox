package br.com.enviromentbox.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlertaDevice.
 */
@Entity
@Table(name = "alerta_device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlertaDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    private Timestamp data_hora;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "processado", nullable = false)
    private Integer processado;

    @ManyToOne(optional = false)
    @NotNull
    private Device device;

    @ManyToOne(optional = false)
    @NotNull
    private Sensor sensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getData_hora() {
        return data_hora;
    }

    public AlertaDevice data_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
        return this;
    }

    public void setData_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
    }

    public Integer getProcessado() {
        return processado;
    }

    public AlertaDevice processado(Integer processado) {
        this.processado = processado;
        return this;
    }

    public void setProcessado(Integer processado) {
        this.processado = processado;
    }

    public Device getDevice() {
        return device;
    }

    public AlertaDevice device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public AlertaDevice sensor(Sensor sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlertaDevice alertaDevice = (AlertaDevice) o;
        if (alertaDevice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alertaDevice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlertaDevice{" +
            "id=" + id +
            ", data_hora='" + data_hora + "'" +
            ", processado='" + processado + "'" +
            '}';
    }
}
