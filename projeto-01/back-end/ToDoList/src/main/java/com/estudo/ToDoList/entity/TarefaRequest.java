package com.estudo.ToDoList.entity;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
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
