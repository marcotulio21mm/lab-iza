package com.estudo.todolist.entity;

import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarefaRequest {
    private TipoTarefa tipo;
    private String titulo;
    private Prioridade prioridade;
    private LocalDate dataPrevista;
    private Integer prazoDias;
    private Boolean status;

}
