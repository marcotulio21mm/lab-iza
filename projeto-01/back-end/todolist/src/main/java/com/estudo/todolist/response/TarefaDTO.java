package com.estudo.todolist.response;

import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
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
