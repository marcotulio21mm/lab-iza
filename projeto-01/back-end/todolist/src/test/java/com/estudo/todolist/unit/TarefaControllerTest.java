package com.estudo.todolist.unit;

import com.estudo.todolist.controller.TarefaController;
import com.estudo.todolist.entity.Tarefa;
import com.estudo.todolist.entity.TarefaData;
import com.estudo.todolist.entity.TarefaLivre;
import com.estudo.todolist.exception.DataInvalidaException;
import com.estudo.todolist.exception.TituloInvalidoException;
import com.estudo.todolist.response.TarefaDTO;
import com.estudo.todolist.response.TarefaDTOData;
import com.estudo.todolist.response.TarefaDTOLivre;
import com.estudo.todolist.response.TarefaDTOPrazo;
import com.estudo.todolist.service.TarefaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TarefaControllerTest {
    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    @Test
    public void testCriarTarefa() {
        String titulo = "Tarefa de teste";
        Tarefa tarefa = new TarefaLivre();
        tarefa.setId(1L);
        Mockito.when(tarefaService.criarTarefa(titulo)).thenReturn(tarefa);

        ResponseEntity<Object> response = tarefaController.criarTarefa(titulo);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(tarefa, response.getBody());
    }
    @Test
    public void testCriarTarefaInvalidTitle() {
        String titulo = "";
        String mensagemErro = "Título não pode ser vazio";
        Mockito.when(tarefaService.criarTarefa(titulo)).thenThrow(new TituloInvalidoException(mensagemErro));

        ResponseEntity<Object> response = tarefaController.criarTarefa(titulo);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(mensagemErro, response.getBody());
    }
    @Test
    public void testCriarTarefaV2Data() {
        TarefaDTOData request = new TarefaDTOData();
        request.setTitulo("Tarefa de teste");
        Tarefa tarefa = new TarefaData();
        tarefa.setId(1L);
        Mockito.when(tarefaService.criarTarefa(request)).thenReturn(tarefa);

        ResponseEntity<Object> response = tarefaController.criarTarefa(request);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(tarefa, response.getBody());
    }

    @Test
    public void testCriarTarefaV2DataInvalidDate() {
        TarefaDTOData request = new TarefaDTOData();
        request.setTitulo("Tarefa de teste");
        request.setDataEsperada(LocalDate.now().minusDays(1)); // Data no passado
        String mensagemErro = "A data prevista deve ser igual ou superior à data atual.";
        Mockito.when(tarefaService.criarTarefa(request)).thenThrow(new DataInvalidaException(mensagemErro));

        ResponseEntity<Object> response = tarefaController.criarTarefa(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(mensagemErro, response.getBody());
    }
    @Test
    public void testCriarTarefaV2Prazo() {
        // Teste de criação de tarefa com DTO de prazo válido
        TarefaDTOPrazo request = new TarefaDTOPrazo();
        request.setTitulo("Tarefa de teste");
        request.setDiasPrevisto(5);
        Tarefa tarefa = new TarefaLivre();
        tarefa.setId(1L);
        Mockito.when(tarefaService.criarTarefa(request)).thenReturn(tarefa);

        ResponseEntity<Object> response = tarefaController.criarTarefa(request);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(tarefa, response.getBody());
    }

    @Test
    public void testCriarTarefaV2PrazoInvalidTitle() {
        // Teste de criação de tarefa com DTO de prazo e título inválido
        TarefaDTOPrazo request = new TarefaDTOPrazo();
        request.setTitulo("");
        String mensagemErro = "Título não pode ser vazio";
        Mockito.when(tarefaService.criarTarefa(request)).thenThrow(new TituloInvalidoException(mensagemErro));

        ResponseEntity<Object> response = tarefaController.criarTarefa(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(mensagemErro, response.getBody());
    }
    @Test
    public void testCriarTarefaV2Livre() {
        // Teste de criação de tarefa com DTO de livre válido
        TarefaDTOLivre request = new TarefaDTOLivre();
        request.setTitulo("Tarefa de teste");
        Tarefa tarefa = new TarefaLivre();
        tarefa.setId(1L);
        Mockito.when(tarefaService.criarTarefa(request)).thenReturn(tarefa);

        ResponseEntity<Object> response = tarefaController.criarTarefa(request);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(tarefa, response.getBody());
    }

    @Test
    public void testCriarTarefaV2LivreInvalidTitle() {
        // Teste de criação de tarefa com DTO de livre e título inválido
        TarefaDTOLivre request = new TarefaDTOLivre();
        request.setTitulo("");
        String mensagemErro = "Título não pode ser vazio";
        Mockito.when(tarefaService.criarTarefa(request)).thenThrow(new TituloInvalidoException(mensagemErro));

        ResponseEntity<Object> response = tarefaController.criarTarefa(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(mensagemErro, response.getBody());
    }

    @Test
    public void testConcluirTarefa() {
        // Teste de conclusão de tarefa
        long id = 1L;
        Tarefa tarefa = new TarefaLivre();
        Mockito.when(tarefaService.mudarStatus(id, true)).thenReturn(tarefa);

        ResponseEntity<Tarefa> response = tarefaController.concluirTarefa(id);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(tarefa, response.getBody());
    }
    @Test
    public void testDesoncluirTarefa() {
        // Teste de desconclusão de tarefa
        long id = 1L;
        Tarefa tarefa = new TarefaLivre();
        Mockito.when(tarefaService.mudarStatus(id, false)).thenReturn(tarefa);

        ResponseEntity<Tarefa> response = tarefaController.desconcluirTarefa(id);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(tarefa, response.getBody());
    }
    @Test
    public void testRetornarTodasTarefas() {
        // Teste para retornar todas as tarefas
        List<TarefaDTO> tarefas = new ArrayList<>();
        Mockito.when(tarefaService.retornarTodasTarefas()).thenReturn(tarefas);

        List<TarefaDTO> response = tarefaController.retornarTodasTarefas();

        Assert.assertEquals(tarefas, response);
    }

    @Test
    public void testRetornarTarefasData() {
        // Teste para retornar tarefas do tipo DATA
        List<TarefaDTOData> tarefas = new ArrayList<>();
        Mockito.when(tarefaService.retornarTarefasData()).thenReturn(tarefas);

        List<TarefaDTOData> response = tarefaController.retornarTarefasData();

        Assert.assertEquals(tarefas, response);
    }

    @Test
    public void testRetornarTarefasPrazo() {
        // Teste para retornar tarefas do tipo PRAZO
        List<TarefaDTOPrazo> tarefas = new ArrayList<>();
        Mockito.when(tarefaService.retornarTarefasPrazo()).thenReturn(tarefas);

        List<TarefaDTOPrazo> response = tarefaController.retornarTarefasPrazo();

        Assert.assertEquals(tarefas, response);
    }

    @Test
    public void testRetornarTarefasLivre() {
        // Teste para retornar tarefas do tipo LIVRE
        List<TarefaDTOLivre> tarefas = new ArrayList<>();
        Mockito.when(tarefaService.retornarTarefasLivre()).thenReturn(tarefas);

        List<TarefaDTOLivre> response = tarefaController.retornarTarefasLivre();

        Assert.assertEquals(tarefas, response);
    }
}
