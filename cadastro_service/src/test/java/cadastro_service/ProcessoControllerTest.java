package cadastro_service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import processo.controller.ProcessoController;
import processo.model.Processo;
import processo.service.ProcessoService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import processo.CadastroServiceApplication;
import processo.exception.ResourceNotFoundException;

@WebMvcTest(controllers = ProcessoController.class)
@ContextConfiguration(classes = CadastroServiceApplication.class)
class ProcessoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessoService processoService;

    @Test
    void salvarProcessoErroValidacao() throws Exception {
        mockMvc.perform(post("/api/Processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void salvarProcesso() throws Exception {
        Processo processo = new Processo(1L, "12345", LocalDate.of(2024, 11, 20), BigDecimal.valueOf(5000), "Objetivo");
        processo.setTipoDeProcesso(1L);

        when(processoService.salvar(any(Processo.class))).thenReturn(processo);

        mockMvc.perform(post("/api/Processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "tipoDeProcesso": 1,
                    "numero": "12345",
                    "dataEntrada": "2024-11-20",
                    "valorRecurso": 5000,
                    "objetivo": "Objetivo"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero").value("12345"))
                .andExpect(jsonPath("$.valorRecurso").value(5000))
                .andExpect(jsonPath("$.objetivo").value("Objetivo"))
                .andExpect(jsonPath("$.tipoDeProcesso").value(1));

        verify(processoService, times(1)).salvar(any(Processo.class));
    }

    @Test
    void atualizarProcesso() throws Exception {
        Processo processoAtualizado = new Processo(1L, "12345", LocalDate.of(2024, 11, 21), BigDecimal.valueOf(10000), "Novo Objetivo");
        processoAtualizado.setTipoDeProcesso(1L);

        when(processoService.atualizar(eq(1L), any(Processo.class))).thenReturn(processoAtualizado);

        mockMvc.perform(put("/api/Processos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "tipoDeProcesso": 1,
                    "numero": "12345",
                    "dataEntrada": "2024-11-21",
                    "valorRecurso": 10000,
                    "objetivo": "Novo Objetivo"
                }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("12345"))
                .andExpect(jsonPath("$.valorRecurso").value(10000))
                .andExpect(jsonPath("$.objetivo").value("Novo Objetivo"))
                .andExpect(jsonPath("$.tipoDeProcesso").value(1));

        verify(processoService, times(1)).atualizar(eq(1L), any(Processo.class));
    }

    @Test
    void listarProcessos() throws Exception {
        Processo processo1 = new Processo(1L, "12345", LocalDate.now(), BigDecimal.valueOf(5000), "Objetivo 1");
        Processo processo2 = new Processo(2L, "67890", LocalDate.now(), BigDecimal.valueOf(10000), "Objetivo 2");
        Page<Processo> page = new PageImpl<>(List.of(processo1, processo2));

        when(processoService.listar(eq("12345"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/Processos?numero=12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].numero").value("12345"))
                .andExpect(jsonPath("$.content[1].numero").value("67890"))
                .andExpect(jsonPath("$.totalElements").value(2));

        verify(processoService, times(1)).listar(eq("12345"), any(Pageable.class));
    }

    @Test
    void excluirProcesso() throws Exception {
        doNothing().when(processoService).excluir(1L);

        mockMvc.perform(delete("/api/Processos/1"))
                .andExpect(status().isNoContent());

        verify(processoService, times(1)).excluir(1L);
    }

   
}
