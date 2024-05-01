package com.estudo.todolist.service;

import com.estudo.todolist.entity.*;
import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import com.estudo.todolist.repository.TarefaRepository;
import com.estudo.todolist.response.TarefaDTO;
import com.estudo.todolist.response.TarefaDTOData;
import com.estudo.todolist.response.TarefaDTOLivre;
import com.estudo.todolist.response.TarefaDTOPrazo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Override
    public Tarefa criarTarefa(String titulo) {
        Tarefa tarefa = new TarefaLivre(titulo, Prioridade.BAIXA, TipoTarefa.LIVRE, false);
        return tarefaRepository.save(tarefa);
    }
    @Override
    public Tarefa criarTarefa(TarefaDTOData request) {
        if (request.getDataEsperada().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data prevista deve ser igual ou superior à data atual.");
        }
        Tarefa tarefa = new TarefaData(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus(), request.getDataEsperada());
        return tarefaRepository.save(tarefa);
    }
    @Override
    public Tarefa criarTarefa(TarefaDTOPrazo request) {
        LocalDate dataPrevista = LocalDate.now().plusDays(request.getDiasPrevisto());
        Tarefa tarefa = new TarefaData(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus(), dataPrevista);
        return tarefaRepository.save(tarefa);
    }
    @Override
    public Tarefa criarTarefa(TarefaDTOLivre request) {
        Tarefa tarefa = new TarefaLivre(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus());
        return tarefaRepository.save(tarefa);
    }
    @Override
    public Tarefa mudarStatus(long id, boolean status) {
        Tarefa tarefa = tarefaRepository.findById(id).orElse(null);
        if (tarefa != null) {
            tarefa.setStatus(status);
            return tarefaRepository.save(tarefa);
        }
        return null;
    }

    @Override
    public void excluirTarefa(long id) {
        tarefaRepository.deleteById(id);
    }

    @Override
    public List<TarefaDTO> retornarTodasTarefas() {
        List<TarefaDTO> tarefas = new ArrayList<>();
        tarefas.addAll(retornarTarefasLivre());
        tarefas.addAll(retornarTarefasData());
        tarefas.addAll(retornarTarefasPrazo());
        return tarefas;
    }

    @Override
    public List<TarefaDTOData> retornarTarefasData() {
        final TipoTarefa tipo = TipoTarefa.DATA;
        List<TarefaDTOData> tarefasData = new ArrayList<>();
        for (TarefaData tarefaData : tarefaRepository.findByTarefaData(TipoTarefa.DATA)) {
            tarefasData.add(new TarefaDTOData(tarefaData.getTitulo(), tarefaData.getPrioridade(),tipo,tarefaData.getStatus(), tarefaData.getDataEsperada()));
        }
        return tarefasData;
    }

    @Override
    public List<TarefaDTOPrazo> retornarTarefasPrazo() {
        final TipoTarefa tipo = TipoTarefa.PRAZO;
        List<TarefaDTOPrazo> tarefasPrazo = new ArrayList<>();
        var tarefas = tarefaRepository.findByTarefaPrazo(TipoTarefa.PRAZO);
        for (TarefaData tarefaPrazo : tarefas) {
            LocalDate hoje = LocalDate.now();
            int diasFaltantes = (int) ChronoUnit.DAYS.between(hoje, tarefaPrazo.getDataEsperada());
            tarefasPrazo.add(new TarefaDTOPrazo(tarefaPrazo.getTitulo(), tarefaPrazo.getPrioridade(),tipo,tarefaPrazo.getStatus(), diasFaltantes));
        }
        return tarefasPrazo;
    }

    @Override
    public List<TarefaDTOLivre> retornarTarefasLivre() {
        final TipoTarefa tipo = TipoTarefa.LIVRE;
        List<TarefaDTOLivre> tarefasLivre = new ArrayList<>();
        for (TarefaLivre tarefaLivre : tarefaRepository.findByLivre(TipoTarefa.LIVRE)) {
            tarefasLivre.add(new TarefaDTOLivre(tarefaLivre.getTitulo(), tarefaLivre.getPrioridade(), tipo,tarefaLivre.getStatus()));
        }
        return tarefasLivre;
    }

}
