package br.com.enviromentbox.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Medicao.
 */
@Entity
@Table(name = "medicao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "valor", precision=10, scale=2, nullable = false)
    private BigDecimal valor;

    @ManyToOne(optional = false)
    @NotNull
    private Sensor sensor;

    @ManyToOne(optional = false)
    @NotNull
    private Device device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Medicao valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Medicao sensor(Sensor sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Device getDevice() {
        return device;
    }

    public Medicao device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medicao medicao = (Medicao) o;
        if (medicao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medicao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medicao{" +
            "id=" + id +
            ", valor='" + valor + "'" +
            '}';
    }
}
