package com.estudo.todolist.service;

import com.estudo.todolist.entity.*;
import com.estudo.todolist.response.TarefaDTO;
import com.estudo.todolist.response.TarefaDTOData;
import com.estudo.todolist.response.TarefaDTOLivre;
import com.estudo.todolist.response.TarefaDTOPrazo;

import java.util.List;

public interface TarefaService {
    Tarefa criarTarefa(String titulo);

    Tarefa criarTarefa(TarefaDTOData request);

    Tarefa criarTarefa(TarefaDTOPrazo request);

    Tarefa criarTarefa(TarefaDTOLivre request);

    Tarefa mudarStatus(long id, boolean status);
    void excluirTarefa(long id);
    List<TarefaDTO> retornarTodasTarefas();

    List<TarefaDTOData> retornarTarefasData();

    List<TarefaDTOPrazo> retornarTarefasPrazo();

    List<TarefaDTOLivre> retornarTarefasLivre();
}


