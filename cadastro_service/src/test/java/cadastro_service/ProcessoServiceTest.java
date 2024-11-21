package cadastro_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestTemplate;
import processo.dto.TipoDeProcesso;
import processo.exception.ResourceNotFoundException;
import processo.model.Processo;
import processo.repository.ProcessoRepository;
import processo.service.ProcessoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcessoServiceTest {

    @Mock
    private ProcessoRepository processoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProcessoService processoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarProcesso_ComSucesso() {
        // Simular resposta do RestTemplate
        TipoDeProcesso tipoMock = new TipoDeProcesso(1L, "Tipo Teste");
        when(restTemplate.getForObject(anyString(), eq(TipoDeProcesso.class))).thenReturn(tipoMock);

        // Simular comportamento do repositório
        Processo processo = new Processo(1L, "12345", LocalDate.now(), new BigDecimal("1000"), "Objetivo");
        when(processoRepository.save(processo)).thenReturn(processo);

        // Executar o método de serviço
        Processo resultado = processoService.salvar(processo);

        // Verificar interações e resultado
        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumero());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(TipoDeProcesso.class));
        verify(processoRepository, times(1)).save(processo);
    }

    @Test
    void atualizarProcesso_ComSucesso() {
        // Simular comportamento do RestTemplate e repositório
        TipoDeProcesso tipoMock = new TipoDeProcesso(1L, "Tipo Atualizado");
        when(restTemplate.getForObject(anyString(), eq(TipoDeProcesso.class))).thenReturn(tipoMock);

        Processo processoExistente = new Processo(1L, "12345", LocalDate.now(), new BigDecimal("1000"), "Objetivo");
        Processo processoAtualizado = new Processo(1L, "54321", LocalDate.now(), new BigDecimal("2000"), "Novo Objetivo");
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoExistente));
        when(processoRepository.save(any(Processo.class))).thenReturn(processoAtualizado);

        // Executar o método de serviço
        Processo resultado = processoService.atualizar(1L, processoAtualizado);

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals("54321", resultado.getNumero());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(TipoDeProcesso.class));
        verify(processoRepository, times(1)).findById(1L);
        verify(processoRepository, times(1)).save(any(Processo.class));
    }

    @Test
    void listarProcessos_ComSucesso() {
        Processo processo1 = new Processo(1L, "12345", LocalDate.now(), new BigDecimal("1000"), "Objetivo 1");
        Processo processo2 = new Processo(2L, "67890", LocalDate.now(), new BigDecimal("2000"), "Objetivo 2");
        Page<Processo> page = new PageImpl<>(Arrays.asList(processo1, processo2));

        when(processoRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Processo> resultado = processoService.listar(null, Pageable.unpaged());

        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        verify(processoRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void excluirProcesso_ComSucesso() {
        when(processoRepository.existsById(1L)).thenReturn(true);

        processoService.excluir(1L);

        verify(processoRepository, times(1)).existsById(1L);
        verify(processoRepository, times(1)).deleteById(1L);
    }

    @Test
    void excluirProcesso_NaoEncontrado() {
        when(processoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> processoService.excluir(1L));
        verify(processoRepository, times(1)).existsById(1L);
    }
}
