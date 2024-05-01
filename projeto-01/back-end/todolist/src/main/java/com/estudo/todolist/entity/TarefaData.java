package com.estudo.todolist.entity;

import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("DATA")
@Getter
@Setter
public class TarefaData extends Tarefa {

    @Column(name = "local_date", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private LocalDate dataEsperada;

    public TarefaData() {
    }

    public TarefaData(LocalDate dataEsperada) {
        this.dataEsperada = dataEsperada;
    }

    public TarefaData(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, LocalDate dataEsperada) {
        super(titulo, prioridade, tipo, status);
        this.dataEsperada = dataEsperada;
    }

    public TarefaData(Long id, String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, LocalDate dataEsperada) {
        super(id, titulo, prioridade, tipo, status);
        this.dataEsperada = dataEsperada;
    }
}
