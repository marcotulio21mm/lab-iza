package com.estudo.todolist.unit;

import com.estudo.todolist.entity.TarefaData;
import com.estudo.todolist.entity.TarefaLivre;
import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import com.estudo.todolist.repository.TarefaRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Test
    public void testFindByTarefaData() {
        TarefaData tarefaData = new TarefaData("Tarefa Data", Prioridade.ALTA, TipoTarefa.DATA, false, LocalDate.now());
        tarefaRepository.save(tarefaData);

        List<TarefaData> result = tarefaRepository.findByTarefaData(TipoTarefa.DATA);
        result = result.stream()
                        .sorted(Comparator.comparingLong(TarefaData::getId))
                        .collect(Collectors.toList());
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("Tarefa Data", result.get(result.size()-1).getTitulo());
    }

    @Test
    public void testFindByTarefaPrazo() {
        TarefaData tarefaPrazo = new TarefaData("Tarefa Prazo", Prioridade.MEDIA, TipoTarefa.PRAZO, false, LocalDate.now().plusDays(3));
        tarefaRepository.save(tarefaPrazo);

        List<TarefaData> result = tarefaRepository.findByTarefaPrazo(TipoTarefa.PRAZO);
        result = result.stream()
                .sorted(Comparator.comparingLong(TarefaData::getId))
                .collect(Collectors.toList());
        Assert.assertEquals(4, result.size());
        Assert.assertEquals("Tarefa Prazo", result.get(result.size()-1).getTitulo());
    }

    @Test
    public void testFindByLivre() {
        TarefaLivre tarefaLivre = new TarefaLivre("Tarefa Livre", Prioridade.BAIXA, TipoTarefa.LIVRE, false);
        tarefaRepository.save(tarefaLivre);

        List<TarefaLivre> result = tarefaRepository.findByLivre(TipoTarefa.LIVRE);
        result = result.stream()
                .sorted(Comparator.comparingLong(TarefaLivre::getId))
                .collect(Collectors.toList());
        Assert.assertEquals(6, result.size());
        Assert.assertEquals("Tarefa Livre", result.get(result.size()-1).getTitulo());
    }
}
