package com.estudo.ToDoList.service;

import com.estudo.ToDoList.entity.*;
import com.estudo.ToDoList.response.TarefaDTO;
import com.estudo.ToDoList.response.TarefaDTOData;
import com.estudo.ToDoList.response.TarefaDTOLivre;
import com.estudo.ToDoList.response.TarefaDTOPrazo;

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


