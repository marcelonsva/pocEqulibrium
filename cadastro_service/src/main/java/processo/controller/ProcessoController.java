package processo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import processo.model.Processo;
import processo.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import processo.repository.ProcessoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/Processos")
@Tag(name = "Processos", description = "Gerenciamento de Processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;
    @Autowired
    private ProcessoRepository processoRepository;

    @PostMapping
    @Operation(summary = "Salvar um novo processo", description = "Cria um novo registro de processo com base nas informações fornecidas.")
    public ResponseEntity<Processo> salvar(@Valid @RequestBody Processo processo) {
        Processo novoProcesso = processoService.salvar(processo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um processo existente", description = "Atualiza as informações de um processo com base no ID fornecido.")
    public ResponseEntity<Processo> atualizar(@PathVariable Long id, @Valid @RequestBody Processo processo) {
        Processo processoAtualizado = processoService.atualizar(id, processo);
        return ResponseEntity.ok(processoAtualizado);
    }

    @GetMapping
    @Operation(summary = "Listar processos", description = "Retorna uma lista paginada de processos. Permite busca pelo número do processo.")
    public ResponseEntity<Page<Processo>> listar(@RequestParam(required = false) String numero, Pageable pageable) {
        Page<Processo> processos = processoService.listar(numero, pageable);
        return ResponseEntity.ok(processos);
    }

    public Page<Processo> listarNovo(String numero, Pageable pageable) {
        if (numero != null && !numero.isEmpty()) {
            return processoRepository.findByNumeroContaining(numero, pageable);
        }
        return processoRepository.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um processo", description = "Remove um processo com base no ID fornecido.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        processoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
