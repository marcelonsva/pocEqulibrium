/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processo.dto;

import java.util.Objects;

/**
 * Classe DTO (Data Transfer Object) para representar um Tipo de Processo.
 * Contém informações básicas sobre o tipo de processo.
 * É usada para transferência de dados entre diferentes camadas do sistema.
 */
public class TipoDeProcesso {

    private Long id;

    private String nome;

    /**
     * Construtor padrão.
     */
    public TipoDeProcesso() {
    }

    /**
     * Construtor com todos os atributos.
     *
     * @param id   ID do Tipo de Processo.
     * @param nome Nome do Tipo de Processo.
     */
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

    // Método toString

    @Override
    public String toString() {
        return "TipoDeProcesso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
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
        TipoDeProcesso that = (TipoDeProcesso) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
