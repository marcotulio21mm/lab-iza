package com.estudo.ToDoList.entity;

import com.estudo.ToDoList.enums.Prioridade;
import com.estudo.ToDoList.enums.TipoTarefa;
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
    @Column(name = "tipo",insertable=false, updatable=false)
    private TipoTarefa tipo;

    @Column(name = "status",nullable = false ,columnDefinition = "boolean default false")
    private Boolean status;

    public Tarefa() {

    }

    public Tarefa(String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status) {
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.tipo = tipo;
        this.status = status;
    }

    public Tarefa(Long id, String titulo, Prioridade prioridade, TipoTarefa tipo, Boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.tipo = tipo;
        this.status = status;
    }
}
