package br.com.enviromentbox.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sensor.
 */
@Entity
@Table(name = "sensor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Device id_device;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "id_tipo_sensor", referencedColumnName = "id")
    private TipoSensor tipoSensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getId_device() {
        return id_device;
    }

    public Sensor id_device(Device device) {
        this.id_device = device;
        return this;
    }

    public void setId_device(Device device) {
        this.id_device = device;
    }

    public TipoSensor getTipoSensor() {
        return tipoSensor;
    }

    public Sensor tipoSensor(TipoSensor tipoSensor) {
        this.tipoSensor = tipoSensor;
        return this;
    }

    public void setTipoSensor(TipoSensor tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sensor sensor = (Sensor) o;
        if (sensor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + id +
            '}';
    }
}
