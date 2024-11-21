/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processo.repository;

import processo.model.Processo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório responsável pela persistência dos dados de Processos.
 * Oferece métodos para manipulação e consulta de dados.
 * Extende JpaRepository para operações padrão de CRUD.
 */
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    /**
     * Busca processos cujo número contenha a string fornecida, com suporte a paginação.
     *
     * @param numero   Número ou parte do número do processo para filtro.
     * @param pageable Configuração de paginação.
     * @return Página contendo os processos filtrados pelo número.
     */
    Page<Processo> findByNumeroContaining(String numero, Pageable pageable);
}
