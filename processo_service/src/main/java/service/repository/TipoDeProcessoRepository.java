package service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.model.TipoDeProcesso;


public interface TipoDeProcessoRepository extends JpaRepository<TipoDeProcesso, Long> {
}
