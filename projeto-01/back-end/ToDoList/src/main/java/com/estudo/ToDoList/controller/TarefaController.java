package com.estudo.ToDoList.controller;

import com.estudo.ToDoList.model.Tarefa;
import com.estudo.ToDoList.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa criarTarefa(@Valid @RequestParam String titulo) {
        return tarefaService.criarTarefa(titulo);
    }

    @PostMapping("/{id}/concluir")
    public Tarefa concluirTarefa(@PathVariable long id) {
        return tarefaService.concluirTarefa(id);
    }

    @DeleteMapping("/{id}")
    public void excluirTarefa(@PathVariable long id) {
        tarefaService.excluirTarefa(id);
    }

    @GetMapping
    public List<Tarefa> retornarTodasTarefas() {
        return tarefaService.retornarTodasTarefas();
    }
}
