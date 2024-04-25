package com.estudo.ToDoList.response;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaDTOPrazo extends TarefaDTO {

    private int diasPrevisto;

    public TarefaDTOPrazo() {
    }

    public TarefaDTOPrazo(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, int diasPrevisto) {
        super(titulo, prioridade, tipo, status);
        this.diasPrevisto = diasPrevisto;
    }
}
