package com.estudo.ToDoList.service;

import com.estudo.ToDoList.entity.*;
import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
import com.estudo.ToDoList.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Tarefa criarTarefa(TarefaRequest request) {
        Tarefa tarefa;
        switch (request.getTipo()) {
            case DATA:
                if (request.getDataPrevista().isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("A data prevista deve ser igual ou superior à data atual.");
                }
                tarefa = new TarefaData(request.getTitulo(), request.getPrioridade(),request.getTipo(), request.getStatus(), request.getDataPrevista());
                break;
            case PRAZO:
                tarefa = new TarefaPrazo(request.getTitulo(), request.getPrioridade(),request.getTipo(), request.getStatus(), request.getPrazoDias());
                break;
            case LIVRE:
                tarefa = new TarefaLivre(request.getTitulo(), request.getPrioridade(),request.getTipo(), request.getStatus());
                break;
            default:
                throw new IllegalArgumentException("Tipo de tarefa inválido");
        }
        return tarefaRepository.save(tarefa);
    }
    public Tarefa retornaTarefa(long id){
        Optional<Tarefa> retorno = tarefaRepository.findById(id);
        if(retorno.isPresent()){
            Tarefa tarefa = retorno.get();
            switch (tarefa.getTipo()) {
                case DATA:
                    break;
                case PRAZO:
                    break;
                case LIVRE:
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de tarefa inválido");
            }
        }
        return null;
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
    public List<Tarefa> retornarTodasTarefas() {
        return tarefaRepository.findAll();
    }
    @Override
    public List<TarefaData> retornarTarefasData() {
        List<TarefaData> tarefasData = tarefaRepository.findByTarefaData(TipoTarefa.DATA);
        return new ArrayList<>(tarefasData);
    }

    @Override
    public List<TarefaPrazo> retornarTarefasPrazo() {
        List<TarefaPrazo> tarefasPrazo = tarefaRepository.findByTarefaPrazo(TipoTarefa.PRAZO);
        return new ArrayList<>(tarefasPrazo);
    }

    @Override
    public List<TarefaLivre> retornarTarefasLivre() {
        List<TarefaLivre> tarefasLivre = tarefaRepository.findByLivre(TipoTarefa.LIVRE);
        return new ArrayList<>(tarefasLivre);
    }
}
