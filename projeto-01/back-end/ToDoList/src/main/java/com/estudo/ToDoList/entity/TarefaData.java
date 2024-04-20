package com.estudo.ToDoList.entity;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
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
    private LocalDate dataPrevista;

    public TarefaData() {
    }

    public TarefaData(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public TarefaData(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, LocalDate dataPrevista) {
        super(titulo, prioridade, tipo, status);
        this.dataPrevista = dataPrevista;
    }

    public TarefaData(Long id, String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, LocalDate dataPrevista) {
        super(id, titulo, prioridade, tipo, status);
        this.dataPrevista = dataPrevista;
    }
}
