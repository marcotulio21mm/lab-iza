package com.estudo.todolist.exception;

public class TarefaNotFoundException extends RuntimeException {
    public TarefaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
