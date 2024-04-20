package com.estudo.ToDoList.entity;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
import jakarta.persistence.*;


import lombok.*;

@Entity
@DiscriminatorValue("LIVRE")
@Getter
@Setter
public class TarefaLivre extends Tarefa {
    public TarefaLivre() {
    }
    public TarefaLivre(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status) {
        super(titulo, prioridade, tipo, status);
    }

    public TarefaLivre(Long id, String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status) {
        super(id, titulo, prioridade, tipo, status);
    }
}
