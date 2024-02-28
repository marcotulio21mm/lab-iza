package com.estudo.ToDoList.service;

import com.estudo.ToDoList.model.Tarefa;

import java.util.List;

public interface TarefaService {
    Tarefa criarTarefa(String titulo);
    Tarefa concluirTarefa(long id);
    void excluirTarefa(long id);
    List<Tarefa> retornarTodasTarefas();
}


