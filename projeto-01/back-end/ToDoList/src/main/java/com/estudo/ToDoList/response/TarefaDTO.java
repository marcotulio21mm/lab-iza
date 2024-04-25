package com.estudo.ToDoList.response;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class TarefaDTO {
    String titulo;
    Prioridade prioridade;
    TipoTarefa tarefa;
    Boolean status;

    public TarefaDTO(String titulo, Prioridade prioridade, TipoTarefa tarefa, Boolean status) {
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.tarefa = tarefa;
        this.status = status;
    }
}
