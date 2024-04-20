package com.estudo.ToDoList.service;

import com.estudo.ToDoList.entity.*;

import java.util.List;

public interface TarefaService {
    Tarefa criarTarefa(String titulo);

    Tarefa criarTarefa(TarefaRequest request);

    Tarefa mudarStatus(long id, boolean status);
    void excluirTarefa(long id);
    List<Tarefa> retornarTodasTarefas();

    List<TarefaData> retornarTarefasData();

    List<TarefaPrazo> retornarTarefasPrazo();

    List<TarefaLivre> retornarTarefasLivre();
}


