package service.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import service.model.TipoDeProcesso;

import java.util.List;
import service.repository.TipoDeProcessoRepository;

@Service
public class TipoDeProcessoService {

    @Autowired
    private TipoDeProcessoRepository repository;
     
    public List<TipoDeProcesso> listarTodos() {
        return repository.findAll();
    }

    public TipoDeProcesso salvar(TipoDeProcesso tipo) {
        return repository.save(tipo);
    }

    public TipoDeProcesso buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}
