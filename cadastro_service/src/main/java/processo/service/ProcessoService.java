/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processo.service;

import jakarta.validation.Valid;
import processo.dto.TipoDeProcesso;
import processo.exception.ResourceNotFoundException;
import processo.model.Processo;
import processo.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Serviço para gerenciamento de processos, responsável pelas operações de
 * negócio.
 */
@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Salva um novo processo no sistema.
     *
     * @param processo Objeto Processo a ser salvo.
     * @return Processo salvo.
     */
    public Processo salvar(@Valid Processo processo) {
        TipoDeProcesso tipo = buscarTipoDeProcesso(processo.getTipoDeProcesso());
        if (tipo == null) {
            throw new ResourceNotFoundException("Tipo de processo não encontrado.");
        }
        return processoRepository.save(processo);
    }

    /**
     * Atualiza os dados de um processo existente.
     *
     * @param id ID do processo a ser atualizado.
     * @param processoAtualizado Dados atualizados do processo.
     * @return Processo atualizado.
     */
    public Processo atualizar(Long id, @Valid Processo processoAtualizado) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado para ID: " + id));

        TipoDeProcesso tipo = buscarTipoDeProcesso(processoAtualizado.getTipoDeProcesso());
        if (tipo == null) {
            throw new ResourceNotFoundException("Tipo de processo não encontrado.");
        }

        processo.setTipoDeProcesso(processoAtualizado.getTipoDeProcesso());
        processo.setDataEntrada(processoAtualizado.getDataEntrada());
        processo.setValorRecurso(processoAtualizado.getValorRecurso());
        processo.setObjetivo(processoAtualizado.getObjetivo());
        return processoRepository.save(processo);
    }

    /**
     * Lista os processos de forma paginada e permite busca por número.
     *
     * @param numero Número do processo para filtro.
     * @param pageable Configuração de paginação.
     * @return Página de Processos filtrados ou não.
     */
    public Page<Processo> listar(String numero, Pageable pageable) {
        return numero != null && !numero.isBlank()
                ? processoRepository.findByNumeroContaining(numero, pageable)
                : processoRepository.findAll(pageable);
    }

    /**
     * Exclui um processo pelo ID.
     *
     * @param id ID do processo a ser excluído.
     */
    public void excluir(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Processo não encontrado");
        }
        processoRepository.deleteById(id);
    }

    /**
     * Busca o tipo de processo a partir de outro sistema via RestTemplate.
     *
     * @param id ID do Tipo de Processo a ser buscado.
     * @return TipoDeProcesso encontrado.
     */
    public TipoDeProcesso buscarTipoDeProcesso(Long id) {
        String url = "http://localhost:8085/api/tiposDeProcesso/" + id;
        return restTemplate.getForObject(url, TipoDeProcesso.class);
    }
}
