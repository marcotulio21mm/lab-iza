package com.estudo.ToDoList.service;

import com.estudo.ToDoList.entity.*;
import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
import com.estudo.ToDoList.exception.DataInvalidaException;
import com.estudo.ToDoList.exception.TarefaNotFoundException;
import com.estudo.ToDoList.exception.TituloInvalidoException;
import com.estudo.ToDoList.repository.TarefaRepository;
import com.estudo.ToDoList.response.TarefaDTO;
import com.estudo.ToDoList.response.TarefaDTOData;
import com.estudo.ToDoList.response.TarefaDTOLivre;
import com.estudo.ToDoList.response.TarefaDTOPrazo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Override
    public Tarefa criarTarefa(String titulo) {
        verificaTitulo(titulo);
        Tarefa tarefa = new TarefaLivre(titulo, Prioridade.BAIXA, TipoTarefa.LIVRE, false);
        return tarefaRepository.save(tarefa);
    }

    private void verificaTitulo(String titulo) {
        if(titulo == null || titulo.trim().isEmpty()){
            throw  new TituloInvalidoException("Título não pode ser vazio");
        }
    }

    @Override
    public Tarefa criarTarefa(TarefaDTOData request) {
        verificaTitulo(request.getTitulo());
        if (request.getDataEsperada().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("A data prevista deve ser igual ou superior à data atual.");
        }
        Tarefa tarefa = new TarefaData(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus(), request.getDataEsperada());
        return tarefaRepository.save(tarefa);
    }
    @Override
    public Tarefa criarTarefa(TarefaDTOPrazo request) {
        verificaTitulo(request.getTitulo());
        LocalDate dataPrevista = LocalDate.now().plusDays(request.getDiasPrevisto());
        Tarefa tarefa = new TarefaData(request.getTitulo(), request.getPrioridade(), request.getTarefa(), request.getStatus(), dataPrevista);
        return tarefaRepository.save(tarefa);
    }
    @Override
    public Tarefa criarTarefa(TarefaDTOLivre request) {
        verificaTitulo(request.getTitulo());
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
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            tarefaRepository.deleteById(id);
        } else {
            throw new TarefaNotFoundException("A tarefa com o ID " + id + " não foi encontrada.");
        }
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
