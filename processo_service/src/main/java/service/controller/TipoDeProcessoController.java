package service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.model.TipoDeProcesso;
import service.services.TipoDeProcessoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
@RestController
@RequestMapping("/api/tiposDeProcesso")
@Tag(name = "Tipos de Processo", description = "APIs para gerenciamento dos tipos de processo")
public class TipoDeProcessoController {

    @Autowired
    private TipoDeProcessoService service;

    @GetMapping
    @Operation(summary = "Listar todos os tipos de processo", description = "Recupera todos os tipos de processo cadastrados.")
    public List<TipoDeProcesso> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    @Operation(summary = "Salvar tipo de processo", description = "Cadastra um novo tipo de processo.")
    public TipoDeProcesso salvar(@RequestBody TipoDeProcesso tipo) {
        return service.salvar(tipo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de processo por ID", description = "Recupera as informações de um tipo de processo pelo seu identificador.")
    public TipoDeProcesso buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
