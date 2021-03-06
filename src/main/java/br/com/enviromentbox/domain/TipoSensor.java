package br.com.enviromentbox.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * A TipoSensor.
 */
@Entity
@Table(name = "tipo_sensor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoSensor implements Serializable {

    public static final BigInteger RUIDO = BigInteger.valueOf(1);
    public static final BigInteger TEMPERATURA = BigInteger.valueOf(2);
    public static final BigInteger UMIDADE = BigInteger.valueOf(3);
    public static final BigInteger MONOXIDO = BigInteger.valueOf(4);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public TipoSensor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoSensor tipoSensor = (TipoSensor) o;
        if (tipoSensor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoSensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoSensor{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            '}';
    }
}
