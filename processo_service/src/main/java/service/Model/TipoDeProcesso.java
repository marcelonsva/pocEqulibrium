package service.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TIPOS_DE_PROCESSO")
public class TipoDeProcesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipos_de_processo_seq")
//    @SequenceGenerator(name = "tipos_de_processo_seq", sequenceName = "C##PROJETO.TIPOS_DE_PROCESSO_SEQ", allocationSize = 1)
    private Long id; // Identificador único do tipo de processo

    @Column(nullable = false, unique = true, length = 100)
    private String nome; // Nome do tipo de processo

    // Construtor padrão
    public TipoDeProcesso() {}

    // Construtor com parâmetros
    public TipoDeProcesso(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método toString para depuração
    @Override
    public String toString() {
        return "TipoDeProcesso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
