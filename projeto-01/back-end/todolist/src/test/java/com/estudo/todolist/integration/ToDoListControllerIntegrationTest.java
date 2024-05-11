package com.estudo.todolist.integration;

import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import com.estudo.todolist.response.TarefaDTOData;
import com.estudo.todolist.response.TarefaDTOPrazo;
import com.estudo.todolist.service.TarefaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ToDoListControllerIntegrationTest {

    @MockBean
    private TarefaService tarefaService;

    @Autowired
    private MockMvc mockMvc;

    private final String pathPadrao = "/tarefas";
    private final String pathVersao2 = "/tarefas/v2";

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void dadoQueUsuarioAcessaSistema_quandoObtemTarefas_entaoListarTarefas() {
        // Fluxo Principal - Visualizar Tarefa
        given().get(pathPadrao).then().statusCode(200);
    }

    @Test
    public void dadoNenhumaTarefa_quandoObtemTarefas_entaoMostrarMensagem() throws Exception {
        // Fluxo Alternativo - Visualizar Tarefa sem tarefas existentes

        // Mock da resposta vazia do servidor
        Mockito.when(tarefaService.retornarTodasTarefas()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    public void dadoQueUsuarioCriaTarefa_quandoTituloEhFornecido_entaoTarefaEhCriada() {
        // Fluxo Principal - Criar Tarefa
        String titulo = "Tarefa de Teste";

        given().param("titulo", titulo)
                .post(pathPadrao)
                .then().statusCode(200)
                .body("titulo", equalTo(titulo));
    }

    @Test
    public void dadoQueUsuarioCriaTarefa_quandoTituloNaoEhFornecido_entaoMostrarMensagemDeErro() {
        // Fluxo Alternativo de exceção - Criar Tarefa
        String titulo = " ";

        given().param("titulo", titulo)
                .post(pathPadrao)
                .then().statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Título não pode ser vazio"));
    }

    @Test
    public void dadoQueUsuarioCriaTarefaComTipoData_quandoDataEhFornecida_entaoTarefaEhCriada() {
        // Fluxo Alternativo - Criar Tarefa Data
        TarefaDTOData tarefaDTOData = new TarefaDTOData("Titulo da Tarefa", Prioridade.ALTA, TipoTarefa.DATA, false, LocalDate.now().plusDays(1));

        given().contentType(ContentType.JSON).body(tarefaDTOData)
                .post(pathVersao2 + "/data")
                .then().statusCode(HttpStatus.OK.value())
                .body("titulo", equalTo("Titulo da Tarefa"));
    }

    @Test
    public void dadoQueUsuarioCriaTarefaComTipoData_quandoDataNaoEhFornecida_entaoMostrarMensagemDeErro() {
        // Fluxo Alternativo de excecao- Criar Tarefa Data sem data
        TarefaDTOData tarefaDTOData = new TarefaDTOData("Titulo da Tarefa", Prioridade.ALTA, TipoTarefa.DATA, false, LocalDate.now().minusDays(1));

        given().contentType(ContentType.JSON).body(tarefaDTOData)
                .post(pathVersao2 + "/data")
                .then().statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("A data prevista deve ser igual ou superior à data atual."));
    }

    @Test
    public void dadoQueUsuarioCriaTarefaComTipoPrazo_quandoPrazoEhFornecido_entaoTarefaEhCriada() {
        // Fluxo Alternativo - Criar Tarefa Prazo
        TarefaDTOPrazo tarefaDTOPrazo = new TarefaDTOPrazo("Titulo da Tarefa", Prioridade.ALTA, TipoTarefa.PRAZO, false, 5);

        given().contentType(ContentType.JSON).body(tarefaDTOPrazo)
                .post(pathVersao2 + "/prazo")
                .then().statusCode(HttpStatus.OK.value())
                .body("titulo", equalTo("Titulo da Tarefa"));
    }

    @Test
    public void dadoQueUsuarioCriaTarefaComTipoPrazo_quandoPrazoNaoEhFornecido_entaoCriaComDataAtual() {
        // Fluxo Alternativo Excecao - Criar Tarefa Prazo sem prazo
        TarefaDTOPrazo tarefaDTOPrazo = new TarefaDTOPrazo("Titulo da Tarefa", Prioridade.ALTA, TipoTarefa.PRAZO, false);

        given().contentType(ContentType.JSON).body(tarefaDTOPrazo)
                .post(pathVersao2 + "/prazo")
                .then().statusCode(HttpStatus.OK.value())
                .body("titulo", equalTo("Titulo da Tarefa"));
    }

    @Test
    public void dadoTarefaExistente_quandoUsuarioExcluiTarefa_entaoTarefaEhRemovida() throws Exception {
        // Fluxo Principal - Deletar tarefa
        long tarefaId = 1;

        given().delete(pathPadrao+"/{id}", tarefaId)
                .then().statusCode(HttpStatus.OK.value())
                .body(equalTo("Tarefa com ID " + tarefaId + " excluída com sucesso."));
    }

    @Test
    public void dadoNenhumaTarefa_quandoUsuarioExcluiTarefa_entaoMostrarMensagem() throws Exception {
        // Fluxo Alternativo Excecao - Deletar Tarefa com id invalido
        long tarefaIdInexistente = 999;

        given().delete(pathPadrao+"/{id}", tarefaIdInexistente)
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("A tarefa com o ID " + tarefaIdInexistente + " não foi encontrada."));
    }

    @Test
    public void dadoTarefaExistente_quandoUsuarioCompletaTarefa_entaoTarefaEhMarcadaComoConcluida() throws Exception {
        // Fluxo Principal - Concluir tarefa
        int tarefaId = 1;

        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .post("/tarefas/{id}/status/concluir", tarefaId)
                .then().statusCode(HttpStatus.OK.value())
                .body("id", equalTo(tarefaId))
                .body("status", equalTo(true));
    }

    @Test
    public void dadoTarefaExistente_quandoUsuarioDesconcluiTarefa_entaoTarefaEhMarcadaComoNaoConcluida() throws Exception {
        // Fluxo Principal - Desconcluir tarefa
        int tarefaId = 1;

        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .post("/tarefas/{id}/status/desconcluir", tarefaId)
                .then().statusCode(HttpStatus.OK.value())
                .body("id", equalTo(tarefaId))
                .body("status", equalTo(false));
    }
}
