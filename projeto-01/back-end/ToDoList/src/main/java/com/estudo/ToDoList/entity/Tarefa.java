package com.estudo.ToDoList.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "TB_TAREFA")
public class Tarefa {
    @Id
    private long id;
    @NotBlank(message = "Título não pode ser vazio")
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false ,columnDefinition = "boolean default false")
    private boolean status;

    public Tarefa() {
    }

    public Tarefa(String titulo, boolean status) {
        this.titulo = titulo;
        this.status = status;
    }

    public Tarefa(long id, String titulo, boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
