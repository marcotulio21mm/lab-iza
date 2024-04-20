package com.estudo.ToDoList.entity;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
import jakarta.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("PRAZO")
@Getter
@Setter
public class TarefaPrazo extends Tarefa {

    @Column(name = "prazo_dias")
    private Integer prazoDias;

    public TarefaPrazo() {
    }

    public TarefaPrazo(Integer prazoDias) {
        this.prazoDias = prazoDias;
    }

    public TarefaPrazo(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, Integer prazoDias) {
        super(titulo, prioridade, tipo, status);
        this.prazoDias = prazoDias;
    }

    public TarefaPrazo(Long id, String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, Integer prazoDias) {
        super(id, titulo, prioridade, tipo, status);
        this.prazoDias = prazoDias;
    }
}
