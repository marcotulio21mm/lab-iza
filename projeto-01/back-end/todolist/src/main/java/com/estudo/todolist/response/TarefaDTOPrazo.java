package com.estudo.todolist.response;

import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaDTOPrazo extends TarefaDTO {

    private int diasPrevisto;

    public TarefaDTOPrazo() {
    }

    public TarefaDTOPrazo(Long id, String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status) {
        super(id, titulo, prioridade, tipo, status);
    }

    public TarefaDTOPrazo(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status) {
        super(titulo, prioridade, tipo, status);
    }

    public TarefaDTOPrazo(Long id, String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status,
            int diasPrevisto) {
        super(id, titulo, prioridade, tipo, status);
        this.diasPrevisto = diasPrevisto;
    }

    public TarefaDTOPrazo(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status,
            int diasPrevisto) {
        super(titulo, prioridade, tipo, status);
        this.diasPrevisto = diasPrevisto;
    }
}
