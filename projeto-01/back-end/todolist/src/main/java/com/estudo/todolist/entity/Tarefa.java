package com.estudo.todolist.entity;

import com.estudo.todolist.enums.Prioridade;
import com.estudo.todolist.enums.TipoTarefa;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

//@MappedSuperclass
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 6, discriminatorType = DiscriminatorType.STRING)
@Table(name = "TB_TAREFA")
public abstract class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título não pode ser vazio")
    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tarefa")
    private TipoTarefa tarefa;

    @Column(name = "status",nullable = false ,columnDefinition = "boolean default false")
    private Boolean status;

    public Tarefa() {

    }

    public Tarefa(String titulo, Prioridade prioridade, TipoTarefa tarefa, Boolean status) {
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.tarefa = tarefa;
        this.status = status;
    }

    public Tarefa(Long id, String titulo, Prioridade prioridade, TipoTarefa tarefa, Boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.tarefa = tarefa;
        this.status = status;
    }
}
