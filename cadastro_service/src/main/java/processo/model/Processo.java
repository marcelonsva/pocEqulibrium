package processo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Representa a entidade Processo, que armazena informações sobre processos.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "processos") // Inclua o schema explicitamente se necessário (ex.: schema = "nome_do_schema")
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "O tipo de processo não pode ser nulo.")
    private Long tipoDeProcesso;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "O número do processo não pode estar vazio.")
    @Size(max = 50, message = "O número do processo deve ter no máximo 50 caracteres.")
    private String numero;

    @Column(nullable = false)
    @NotNull(message = "A data de entrada não pode ser nula.")
    private LocalDate dataEntrada;

    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull(message = "O valor do recurso não pode ser nulo.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor do recurso deve ser maior que zero.")
    private BigDecimal valorRecurso;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "O objetivo não pode estar vazio.")
    @Size(max = 255, message = "O objetivo deve ter no máximo 255 caracteres.")
    private String objetivo;

    // Construtor padrão
    public Processo() {
    }

    /**
     * Construtor completo da entidade Processo.
     *
     * @param tipoDeProcesso Tipo do processo.
     * @param numero Número do processo.
     * @param dataEntrada Data de entrada do processo.
     * @param valorRecurso Valor associado ao processo.
     * @param objetivo Objetivo do processo.
     */
    public Processo(Long tipoDeProcesso, String numero, LocalDate dataEntrada, BigDecimal valorRecurso, String objetivo) {
        this.tipoDeProcesso = tipoDeProcesso;
        this.numero = numero;
        this.dataEntrada = dataEntrada;
        this.valorRecurso = valorRecurso;
        this.objetivo = objetivo;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipoDeProcesso() {
        return tipoDeProcesso;
    }

    public void setTipoDeProcesso(Long tipoDeProcesso) {
        this.tipoDeProcesso = tipoDeProcesso;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public BigDecimal getValorRecurso() {
        return valorRecurso;
    }

    public void setValorRecurso(BigDecimal valorRecurso) {
        this.valorRecurso = valorRecurso;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    // Método toString
    @Override
    public String toString() {
        return "Processo{"
                + "id=" + id
                + ", tipoDeProcesso=" + tipoDeProcesso
                + ", numero='" + numero + '\''
                + ", dataEntrada=" + dataEntrada
                + ", valorRecurso=" + valorRecurso
                + ", objetivo='" + objetivo + '\''
                + '}';
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Processo processo = (Processo) o;
        return Objects.equals(id, processo.id)
                && Objects.equals(numero, processo.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero);
    }
}
