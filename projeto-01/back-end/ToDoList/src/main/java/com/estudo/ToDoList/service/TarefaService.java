package com.estudo.ToDoList.service;

import com.estudo.ToDoList.entity.Tarefa;

import java.util.List;

public interface TarefaService {
    Tarefa criarTarefa(String titulo);
    Tarefa mudarStatus(long id, boolean status);
    void excluirTarefa(long id);
    List<Tarefa> retornarTodasTarefas();
}


