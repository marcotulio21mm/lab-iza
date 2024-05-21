package com.estudo.todolist.unit;

import com.estudo.todolist.entity.Tarefa;
import com.estudo.todolist.entity.TarefaData;
import com.estudo.todolist.entity.TarefaLivre;
import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import com.estudo.todolist.exception.DataInvalidaException;
import com.estudo.todolist.exception.TarefaNotFoundException;
import com.estudo.todolist.exception.TituloInvalidoException;
import com.estudo.todolist.repository.TarefaRepository;
import com.estudo.todolist.response.TarefaDTO;
import com.estudo.todolist.response.TarefaDTOData;
import com.estudo.todolist.response.TarefaDTOLivre;
import com.estudo.todolist.response.TarefaDTOPrazo;
import com.estudo.todolist.service.TarefaServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TarefaServiceImplTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    @Test(expected = TituloInvalidoException.class)
    public void testCriarTarefa_TituloInvalido() {
        // Teste para criar uma tarefa com título inválido (vazio)
        tarefaService.criarTarefa("");
    }

    @Test
    public void testCriarTarefa_TituloValido() {
        // Teste para criar uma tarefa com título válido
        String titulo = "Título válido";
        Tarefa tarefa = new TarefaLivre(titulo, Prioridade.BAIXA, TipoTarefa.LIVRE, false);
        Mockito.when(tarefaRepository.save(Mockito.any())).thenReturn(tarefa);

        Tarefa result = tarefaService.criarTarefa(titulo);

        Assert.assertNotNull(result);
        Assert.assertEquals(titulo, result.getTitulo());
    }

    @Test(expected = DataInvalidaException.class)
    public void testCriarTarefa_DataInvalida() {
        // Teste para criar uma tarefa com data inválida (anterior à data atual)
        TarefaDTOData request = new TarefaDTOData();
        request.setTitulo("Título");
        request.setPrioridade(Prioridade.ALTA);
        request.setTarefa(TipoTarefa.DATA);
        request.setStatus(false);
        request.setDataEsperada(LocalDate.now().minusDays(1));

        tarefaService.criarTarefa(request);
    }

    @Test
    public void testCriarTarefa_DataValida() {
        // Teste para criar uma tarefa com data válida (igual ou posterior à data atual)
        LocalDate dataEsperada = LocalDate.now().plusDays(1);
        TarefaDTOData request = new TarefaDTOData();
        request.setTitulo("Título");
        request.setPrioridade(Prioridade.ALTA);
        request.setTarefa(TipoTarefa.DATA);
        request.setStatus(false);
        request.setDataEsperada(dataEsperada);

        Tarefa tarefa = new TarefaData(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus(), dataEsperada);
        Mockito.when(tarefaRepository.save(Mockito.any())).thenReturn(tarefa);

        Tarefa result = tarefaService.criarTarefa(request);

        Assert.assertNotNull(result);
        Assert.assertEquals(dataEsperada, ((TarefaData) result).getDataEsperada());
    }
    @Test(expected = TituloInvalidoException.class)
    public void testCriarTarefaPrazo_TituloInvalido() {
        // Teste para criar uma tarefa com título inválido (vazio)
        TarefaDTOPrazo request = new TarefaDTOPrazo();
        request.setTitulo("");

        tarefaService.criarTarefa(request);
    }

    @Test
    public void testCriarTarefaPrazo_TituloValido() {
        // Teste para criar uma tarefa com título válido
        String titulo = "Título válido";
        TarefaDTOPrazo request = new TarefaDTOPrazo();
        request.setTitulo(titulo);
        request.setPrioridade(Prioridade.ALTA);
        request.setTarefa(TipoTarefa.PRAZO);
        request.setStatus(false);
        request.setDiasPrevisto(7);

        LocalDate dataPrevista = LocalDate.now().plusDays(request.getDiasPrevisto());
        Tarefa tarefa = new TarefaData(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus(), dataPrevista);
        Mockito.when(tarefaRepository.save(Mockito.any())).thenReturn(tarefa);

        Tarefa result = tarefaService.criarTarefa(request);

        Assert.assertNotNull(result);
        Assert.assertEquals(titulo, result.getTitulo());
        Assert.assertEquals(dataPrevista, ((TarefaData) result).getDataEsperada());
    }

    @Test(expected = TituloInvalidoException.class)
    public void testCriarTarefaLivre_TituloInvalido() {
        // Teste para criar uma tarefa livre com título inválido (vazio)
        TarefaDTOLivre request = new TarefaDTOLivre();
        request.setTitulo("");

        tarefaService.criarTarefa(request);
    }

    @Test
    public void testCriarTarefaLivre_TituloValido() {
        // Teste para criar uma tarefa livre com título válido
        String titulo = "Título válido";
        TarefaDTOLivre request = new TarefaDTOLivre();
        request.setTitulo(titulo);
        request.setPrioridade(Prioridade.ALTA);
        request.setTarefa(TipoTarefa.PRAZO);
        request.setStatus(false);

        Tarefa tarefa = new TarefaLivre(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus());
        Mockito.when(tarefaRepository.save(Mockito.any())).thenReturn(tarefa);

        Tarefa result = tarefaService.criarTarefa(request);

        Assert.assertNotNull(result);
        Assert.assertEquals(titulo, result.getTitulo());
    }
    @Test
    public void testMudarStatus_TarefaExistente() {
        // Teste para mudar o status de uma tarefa existente
        long id = 1;
        boolean novoStatus = true;
        Tarefa tarefa = new TarefaLivre();
        Mockito.when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
        Mockito.when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa result = tarefaService.mudarStatus(id, novoStatus);

        Assert.assertNotNull(result);
        Assert.assertEquals(novoStatus, result.getStatus());
    }

    @Test
    public void testMudarStatus_TarefaNaoExistente() {
        // Teste para tentar mudar o status de uma tarefa inexistente
        long id = 1;
        boolean novoStatus = true;
        Mockito.when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        Tarefa result = tarefaService.mudarStatus(id, novoStatus);

        Assert.assertNull(result);
    }

    @Test
    public void testExcluirTarefa_TarefaExistente() {
        // Teste para excluir uma tarefa existente
        long id = 1;
        Mockito.when(tarefaRepository.findById(id)).thenReturn(Optional.of(new TarefaLivre()));

        tarefaService.excluirTarefa(id);

        Mockito.verify(tarefaRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = TarefaNotFoundException.class)
    public void testExcluirTarefa_TarefaNaoExistente() {
        // Teste para tentar excluir uma tarefa inexistente
        long id = 1;
        Mockito.when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        tarefaService.excluirTarefa(id);
    }
    @Test
    public void testRetornarTodasTarefas() {
        // Mock de tarefas retornadas pelos métodos individuais
        List<TarefaLivre> tarefasLivre = new ArrayList<>();
        tarefasLivre.add(new TarefaLivre("Tarefa 1", Prioridade.ALTA, TipoTarefa.LIVRE, false));
        List<TarefaData> tarefasData = new ArrayList<>();
        tarefasData.add(new TarefaData("Tarefa 2", Prioridade.BAIXA, TipoTarefa.DATA, false, LocalDate.now()));
        List<TarefaData> tarefasPrazo = new ArrayList<>();
        tarefasPrazo.add(new TarefaData("Tarefa 3", Prioridade.MEDIA, TipoTarefa.PRAZO, false, LocalDate.now().plusDays(7)));

        Mockito.when(tarefaRepository.findByLivre(TipoTarefa.LIVRE)).thenReturn(tarefasLivre);
        Mockito.when(tarefaRepository.findByTarefaData(TipoTarefa.DATA)).thenReturn(tarefasData);
        Mockito.when(tarefaRepository.findByTarefaPrazo(TipoTarefa.PRAZO)).thenReturn(tarefasPrazo);

        List<TarefaDTO> resultado = tarefaService.retornarTodasTarefas();

        Assert.assertEquals(3, resultado.size()); // Verificando se todas as tarefas foram retornadas
    }

    @Test
    public void testRetornarTarefasData() {
        // Mock de tarefas de data
        List<TarefaData> tarefasData = new ArrayList<>();
        tarefasData.add(new TarefaData("Tarefa 1", Prioridade.ALTA, TipoTarefa.DATA, false, LocalDate.now()));

        Mockito.when(tarefaRepository.findByTarefaData(TipoTarefa.DATA)).thenReturn(tarefasData);

        List<TarefaDTOData> resultado = tarefaService.retornarTarefasData();

        Assert.assertEquals(1, resultado.size()); // Verificando se a tarefa de data foi retornada
    }

    @Test
    public void testRetornarTarefasPrazo() {
        // Mock de tarefas de prazo
        List<TarefaData> tarefasPrazo = new ArrayList<>();
        tarefasPrazo.add(new TarefaData("Tarefa 1", Prioridade.MEDIA, TipoTarefa.PRAZO, false, LocalDate.now().plusDays(7)));

        Mockito.when(tarefaRepository.findByTarefaPrazo(TipoTarefa.PRAZO)).thenReturn(tarefasPrazo);

        List<TarefaDTOPrazo> resultado = tarefaService.retornarTarefasPrazo();

        Assert.assertEquals(1, resultado.size()); // Verificando se a tarefa de prazo foi retornada
    }

    @Test
    public void testRetornarTarefasLivre() {
        // Mock de tarefas livres
        List<TarefaLivre> tarefasLivre = new ArrayList<>();
        tarefasLivre.add(new TarefaLivre("Tarefa 1", Prioridade.ALTA, TipoTarefa.LIVRE, false));

        Mockito.when(tarefaRepository.findByLivre(TipoTarefa.LIVRE)).thenReturn(tarefasLivre);

        List<TarefaDTOLivre> resultado = tarefaService.retornarTarefasLivre();

        Assert.assertEquals(1, resultado.size()); // Verificando se a tarefa livre foi retornada
    }
}