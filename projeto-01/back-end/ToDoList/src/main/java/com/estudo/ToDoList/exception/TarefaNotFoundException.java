package com.estudo.ToDoList.exception;

public class TarefaNotFoundException extends RuntimeException {
    public TarefaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
