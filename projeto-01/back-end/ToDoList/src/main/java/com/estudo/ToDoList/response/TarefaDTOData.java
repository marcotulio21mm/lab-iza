package com.estudo.ToDoList.response;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class TarefaDTOData extends TarefaDTO {
    private LocalDate dataEsperada;

    public TarefaDTOData() {
    }

    public TarefaDTOData(java.time.LocalDate dataEsperada) {
        this.dataEsperada = dataEsperada;
    }

    public TarefaDTOData(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status, LocalDate dataEsperada) {
        super(titulo, prioridade, tipo, status);
        this.dataEsperada = dataEsperada;
    }

}
